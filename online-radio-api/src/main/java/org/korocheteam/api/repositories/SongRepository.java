package org.korocheteam.api.repositories;

import org.korocheteam.api.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Integer> {
	Optional<Song> findByTitle(String title);
	Optional<Song> findByTitleAndArtist(String title, String artist);
	Optional<Song> findById(Integer id);
}
