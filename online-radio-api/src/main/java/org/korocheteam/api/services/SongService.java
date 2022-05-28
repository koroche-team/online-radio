package org.korocheteam.api.services;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.exceptions.SongNotFoundException;
import org.korocheteam.api.models.Song;
import org.korocheteam.api.repositories.SongRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    private Integer songId = 1;

    public Song getNextSong() {
        try {
            Song song = songRepository.findById(songId).orElseThrow(SongNotFoundException::new);
            songId++;
            return song;
        } catch (SongNotFoundException e) {
            // database of songs ran out, starting from first track
            songId = 1;
            return getNextSong();
        }
    }
}
