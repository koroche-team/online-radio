package org.korocheteam.api.services;

import com.gmail.kunicins.olegs.libshout.Libshout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.korocheteam.api.exceptions.AudioServiceException;
import org.korocheteam.api.models.Song;
import org.korocheteam.api.models.StreamStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AudioService {
    @Value("${icecast.host}")
    private String icecastHost;
    @Value("${icecast.password}")
    private String icecastPassword;
    @Value("${icecast.mount}")
    private String icecastMount;

    private final SongService songService;
    private volatile StreamStatus streamStatus;

    private final String audioPath;
    private final String coversPath;

    public void runAudioStream() {
        new Thread(() -> {
            try (Libshout icecast = new Libshout()) {
                byte[] buffer = new byte[1024];
                icecast.setHost(icecastHost);
                icecast.setPort(8000);
                icecast.setProtocol(Libshout.PROTOCOL_HTTP);
                icecast.setPassword(icecastPassword);
                icecast.setMount(icecastMount);
                icecast.setFormat(Libshout.FORMAT_MP3);
                icecast.open();

                this.streamStatus = new StreamStatus();
                while (true) {
                    try {
                        Song song = songService.getNextSong();
                        AudioInputStream mp3 = AudioSystem.getAudioInputStream(new File(song.getPath()));
                        streamStatus.setCurrentSong(song);
                        streamStatus.setLength(Math.round(2.0 * mp3.available() / mp3.getFormat().getFrameRate()));
                        streamStatus.setTime(0L);
                        log.info("Playing " + song);
                        int read = mp3.read(buffer);
                        while (read > 0) {
                            // TODO: update likes status
                            double currentTime = streamStatus.getLength() - 2.0 * mp3.available() / mp3.getFormat().getFrameRate();
                            streamStatus.setTime(Math.round(currentTime));
                            icecast.send(buffer, read);
                            read = mp3.read(buffer);
                        }
                    } catch (UnsupportedAudioFileException e) {
                        log.error("Audio is not supported", e);
                    } catch (IOException e) {
                        log.error("Unable to open file or send audio to server", e);
                    }
                }
            } catch (IOException e) {
                throw new AudioServiceException("Audio server failed", e);
            }
        }).start();
    }

    public StreamStatus getStreamStatus() {
        return this.streamStatus;
    }

    public void updateMusicList() {
        try {
            Set<Path> fileList = listFiles(audioPath);
            for (Path filePath: fileList) {
                try {
                    addTrack(filePath);
                } catch (AudioServiceException e) {
                    log.error("Unable to add track at " + filePath.toAbsolutePath());
                }
            }
        } catch (IOException e) {
            throw new AudioServiceException("Unable to update audio list", e);
        }
    }

    public void addTrack(Path path) throws AudioServiceException {
        try {
            MP3File audioFile = (MP3File) AudioFileIO.read(path.toFile());
            Tag tag = audioFile.getTag();

            Artwork cover = tag.getFirstArtwork();
            BufferedImage image = cover.getImage();
            String pathToCover = coversPath + "/" + UUID.randomUUID() + ".jpg";
            ImageIO.write(image, "jpg", new File(pathToCover));

            Song song = Song.builder()
                    .title(tag.getFirst(FieldKey.TITLE))
                    .album(tag.getFirst(FieldKey.ALBUM))
                    .artist(tag.getFirst(FieldKey.ARTIST))
                    .path(path.toString())
                    .cover(pathToCover)
                    .likes(Collections.emptyList())
                    .build();

            if (songService.doesExists(song)) {
                log.trace("Song already found in database: " + song.toString());
                return;
            }

            log.trace("New song added: " + song.toString());
            songService.addSong(song);
        } catch (IOException | CannotReadException | TagException |
                 ReadOnlyFileException | InvalidAudioFrameException e) {
            throw new AudioServiceException("Unable add track at" + path.toAbsolutePath(), e);
        }
    }

    public Set<Path> listFiles(String dir) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .collect(Collectors.toSet());
        }
    }
}
