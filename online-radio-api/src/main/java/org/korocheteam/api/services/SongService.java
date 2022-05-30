package org.korocheteam.api.services;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.exceptions.SongNotFoundException;
import org.korocheteam.api.models.Song;
import org.korocheteam.api.models.dtos.SongDto;
import org.korocheteam.api.models.dtos.responses.TopSongsResponse;
import org.korocheteam.api.repositories.LikeRepository;
import org.korocheteam.api.repositories.SongRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private Iterator<Song> songIterator;
    private Page<Song> page;
    private Pageable pageRequest;

    private final LikeRepository likeRepository;

    public Song getNextSong() {
        if (songIterator == null) {
            pageRequest = PageRequest.of(0, 50);
            page = songRepository.findAll(pageRequest);
            songIterator = page.iterator();
        }
        if (!songIterator.hasNext()) {
            System.out.println(page.getNumber());
            if (page.getNumber() ==  page.getTotalPages()-1) {
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

    public void cleanRepository() {
        songRepository.deleteAll();
    }

    public TopSongsResponse getTopTenSongs() {
        List<SongDto> songs = SongDto.from(songRepository.findAll());
        songs.sort(Comparator.comparingInt(SongDto::getAmountOfLikes).reversed());

        return TopSongsResponse.builder()
                .songs(songs.stream().limit(10).collect(Collectors.toList()))
                .build();
    }

}
