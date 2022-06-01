package org.korocheteam.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.Genre;
import org.korocheteam.api.models.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongServicePagesContainer {
    private Map<Genre, Iterator<Song>> songIterators = new HashMap<>();
    private Map<Genre, Page<Song>> pages = new HashMap<>();
    private Map<Genre, Pageable> pageables = new HashMap<>();


    public Iterator<Song> getIterator(Genre genre) {
        return songIterators.get(genre);
    }

    public Page<Song> getPage(Genre genre) {
        return pages.get(genre);
    }

    public Pageable getPageable(Genre genre) {
        return pageables.get(genre);
    }

    public Iterator<Song> setIterator(Genre genre, Iterator<Song> iterator) {
        songIterators.put(genre, iterator);
        return iterator;
    }

    public Page<Song> setPage(Genre genre, Page<Song> page) {
        pages.put(genre, page);
        return page;
    }

    public Pageable setPageable(Genre genre, Pageable pageable) {
        pageables.put(genre, pageable);
        return pageable;
    }
}
