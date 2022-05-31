package org.korocheteam.api.repositories;

import org.korocheteam.api.models.SongLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<SongLike, Long> {
	Optional<SongLike> findByAccountEmailAndSongId(String email, Integer songId);
}
