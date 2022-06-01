package org.korocheteam.api.repositories;

import org.korocheteam.api.models.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Integer> {
	Optional<Song> findByTitle(String title);
	Optional<Song> findByTitleAndArtist(String title, String artist);
	Optional<Song> findById(Integer id);
	Optional<Song> findByHash(String hash);

	Page<Song> findAllByGenre(Pageable pageable, Song.Genre genre);

	List<Song> findAllByGenre(Song.Genre genre);
}
