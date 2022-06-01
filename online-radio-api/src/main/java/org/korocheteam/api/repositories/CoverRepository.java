package org.korocheteam.api.repositories;

import org.korocheteam.api.models.Cover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CoverRepository extends JpaRepository<Cover, Long> {

    Optional<Cover> findByHash(String hash);

    Optional<Cover> findByUuid(UUID uuid);
}
