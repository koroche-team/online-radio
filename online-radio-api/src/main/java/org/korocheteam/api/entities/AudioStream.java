package org.korocheteam.api.entities;

import com.gmail.kunicins.olegs.libshout.Libshout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.korocheteam.api.exceptions.AudioStreamException;
import org.korocheteam.api.models.Cover;
import org.korocheteam.api.models.Genre;
import org.korocheteam.api.models.Song;
import org.korocheteam.api.models.StreamStatus;
import org.korocheteam.api.services.CoverService;
import org.korocheteam.api.services.SongService;
import org.korocheteam.api.utils.ImageUtil;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@RequiredArgsConstructor
public class AudioStream {
    private final String icecastHost;
    private final String icecastPassword;

    private final SongService songService;
    private final CoverService coverService;
    private volatile StreamStatus streamStatus;

    private final String audioPath;
    private final String coversPath;

    private final Genre genre;

    private final ImageUtil imageUtil;

    public void runAudioStream() {
        new Thread(() -> {
            try (Libshout icecast = new Libshout()) {
                byte[] buffer = new byte[1024];
                icecast.setHost(icecastHost);
                icecast.setPort(8000);
                icecast.setProtocol(Libshout.PROTOCOL_HTTP);
                icecast.setPassword(icecastPassword);
                icecast.setMount("/"+genre.toString().toLowerCase());
                icecast.setFormat(Libshout.FORMAT_MP3);
                icecast.open();

                this.streamStatus = new StreamStatus();
                while (true) {
                    try {
                        Song song = songService.getNextSong(genre);
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
                throw new AudioStreamException("Audio server failed", e);
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
                } catch (AudioStreamException e) {
                    log.error("Unable to add track at " + filePath.toAbsolutePath());
                }
            }
        } catch (IOException e) {
            throw new AudioStreamException("Unable to update audio list", e);
        }
    }

    public void addTrack(Path path) throws AudioStreamException {
        try {
            MP3File audioFile = (MP3File) AudioFileIO.read(path.toFile());
            Tag tag = audioFile.getTag();

            // process artwork
            Artwork artwork = tag.getFirstArtwork();
            String coverHash = DigestUtils.md5Hex(artwork.getBinaryData());
            Optional<Cover> foundCover = coverService.getCoverByHash(coverHash);
            Cover cover;
            if (foundCover.isEmpty()) {
                UUID coverUuid = UUID.randomUUID();
                BufferedImage image = artwork.getImage();
                String pathToCover = coversPath + "/" + coverUuid + ".jpg";
                File pic = new File(pathToCover);
                ImageIO.write(image, "jpg", pic);

                String url = imageUtil.createUrl(pic);

                cover = Cover.builder()
                        .path(url)
                        .hash(coverHash)
                        .uuid(coverUuid)
                        .build();
                coverService.saveCover(cover);
            } else {
                cover = foundCover.get();
            }

            // process song
            String songHash;
            try (InputStream is = new FileInputStream(path.toString())) {
                songHash = DigestUtils.md5Hex(is);
            } catch (IOException e) {
                throw new AudioStreamException("Unable to read song");
            }

            Song song = Song.builder()
                    .title(tag.getFirst(FieldKey.TITLE))
                    .album(tag.getFirst(FieldKey.ALBUM))
                    .artist(tag.getFirst(FieldKey.ARTIST))
                    .genre(Genre.valueOf(tag.getFirst(FieldKey.GENRE).toUpperCase(Locale.ROOT)))
                    .hash(songHash)
                    .path(path.toString())
                    .cover(cover)
                    .likes(Collections.emptyList())
                    .build();

            // ignore songs from other genre
            if (!song.getGenre().equals(this.genre)) {
                return;
            }

            if (songService.doesExists(song)) {
                log.trace("Song already found in database: " + song);
                return;
            }

            log.trace("New song added: " + song);
            songService.addSong(song);
        } catch (IOException | CannotReadException | TagException |
                 ReadOnlyFileException | InvalidAudioFrameException e) {
            throw new AudioStreamException("Unable add track at" + path.toAbsolutePath(), e);
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
