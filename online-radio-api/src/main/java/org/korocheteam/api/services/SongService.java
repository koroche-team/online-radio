package org.korocheteam.api.services;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.entities.SongServicePagesContainer;
import org.korocheteam.api.models.Genre;
import org.korocheteam.api.models.Song;
import org.korocheteam.api.models.dtos.SongDto;
import org.korocheteam.api.models.dtos.responses.TopSongsResponse;
import org.korocheteam.api.repositories.SongRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    private final SongServicePagesContainer pagesContainer;

    public Song getNextSong(Genre genre) {
        if (pagesContainer.getPage(genre) == null) {
            Pageable request = pagesContainer.setPageable(genre, PageRequest.of(0, 50));
            Page<Song> page = pagesContainer.setPage(genre, songRepository.findAllByGenre(request, genre));
            pagesContainer.setIterator(genre, page.iterator());
        }
        if (!pagesContainer.getIterator(genre).hasNext()) {
            Page<Song> page = pagesContainer.getPage(genre);
            Pageable pageable = pagesContainer.getPageable(genre);
            if (page.getNumber() ==  page.getTotalPages()-1) {
                pagesContainer.setPageable(genre, pageable.first());
            } else {
                pagesContainer.setPageable(genre, pageable.next());
            }
            page = pagesContainer.setPage(genre, songRepository.findAllByGenre(pageable, genre));
            pagesContainer.setIterator(genre, page.iterator());
        }
        return pagesContainer.getIterator(genre).next();
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public boolean doesExists(Song song) {
        return songRepository.findByHash(song.getHash()).isPresent();
    }

    public void cleanRepository() {
        songRepository.deleteAll();
    }

    public TopSongsResponse getTopTenSongs(String genre) {
        List<SongDto> songs = SongDto.from(songRepository.findAllByGenre(Genre.valueOf(genre.toUpperCase())));
        songs.sort(Comparator.comparingInt(SongDto::getAmountOfLikes).reversed());

        return TopSongsResponse.builder()
                .songs(songs.stream().limit(10).collect(Collectors.toList()))
                .build();
    }

}
