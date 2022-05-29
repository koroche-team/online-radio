package org.korocheteam.api.services;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.exceptions.SongNotFoundException;
import org.korocheteam.api.models.Song;
import org.korocheteam.api.repositories.SongRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private Iterator<Song> songIterator;
    private Page<Song> page;
    private Pageable pageRequest;

    public Song getNextSong() {
        if (songIterator == null) {
            pageRequest = PageRequest.of(0, 50);
            page = songRepository.findAll(pageRequest);
            songIterator = page.iterator();
        }
        if (!songIterator.hasNext()) {
            if (page.getNumber() == page.getTotalPages()) {
                pageRequest = pageRequest.first();
            } else {
                pageRequest = pageRequest.next();
            }
            page = songRepository.findAll(pageRequest);
            songIterator = page.iterator();
        }
        return songIterator.next();
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public boolean doesExists(Song song) {
        // TODO: add proper song check with file hash value
        return songRepository.exists(Example.of(song));
    }

}
