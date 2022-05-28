package org.korocheteam.api.services;

import com.gmail.kunicins.olegs.libshout.Libshout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.korocheteam.api.exceptions.AudioServiceException;
import org.korocheteam.api.models.Song;
import org.korocheteam.api.models.StreamStatus;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AudioService {
    private final SongService songService;
    private volatile StreamStatus streamStatus;

    public void runAudioStream() {
        new Thread(() -> {
            try (Libshout icecast = new Libshout()) {
                byte[] buffer = new byte[1024];
                icecast.setHost("localhost");
                icecast.setPort(8000);
                icecast.setProtocol(Libshout.PROTOCOL_HTTP);
                icecast.setPassword("qwerty");
                icecast.setMount("/radio");
                icecast.setFormat(Libshout.FORMAT_MP3);
                icecast.open();

                this.streamStatus = new StreamStatus();
                while (true) {
                    try {
                        Song song = songService.getNextSong();
                        AudioInputStream mp3 = AudioSystem.getAudioInputStream(new File(song.getPath()));
                        streamStatus.setCurrentSong(song);
                        streamStatus.setTime(0);
                        int read = mp3.read(buffer);
                        while (read > 0) {
                            double currentTime = 1000.0 * mp3.available()/ mp3.getFrameLength() / mp3.getFormat().getFrameRate();
                            streamStatus.setTime((int) currentTime);
                            icecast.send(buffer, read);
                            read = mp3.read(buffer);
                        }
                    } catch (UnsupportedAudioFileException e) {
                        log.error("Audio is not supported");
                    } catch (IOException e) {
                        throw new AudioServiceException("Unable to open file or send audio to server");
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
}
