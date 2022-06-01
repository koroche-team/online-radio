package org.korocheteam.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Map<Song.Genre, Iterator<Song>> songIterators = new HashMap<>();
    private Map<Song.Genre, Page<Song>> pages = new HashMap<>();
    private Map<Song.Genre, Pageable> pageables = new HashMap<>();


    public Iterator<Song> getIterator(Song.Genre genre) {
        return songIterators.get(genre);
    }

    public Page<Song> getPage(Song.Genre genre) {
        return pages.get(genre);
    }

    public Pageable getPageable(Song.Genre genre) {
        return pageables.get(genre);
    }

    public Iterator<Song> setIterator(Song.Genre genre, Iterator<Song> iterator) {
        songIterators.put(genre, iterator);
        return iterator;
    }

    public Page<Song> setPage(Song.Genre genre, Page<Song> page) {
        pages.put(genre, page);
        return page;
    }

    public Pageable setPageable(Song.Genre genre, Pageable pageable) {
        pageables.put(genre, pageable);
        return pageable;
    }
}
