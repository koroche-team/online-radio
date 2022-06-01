package org.korocheteam.api.services;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.exceptions.CoverNotFoundException;
import org.korocheteam.api.models.Cover;
import org.korocheteam.api.repositories.CoverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoverService {

    private final CoverRepository coverRepository;

    public Optional<Cover> getCoverByHash(String hash) {
        return coverRepository.findByHash(hash);
    }

    public Cover getCoverByUuid(String uuid) {
        return coverRepository.findByUuid(UUID.fromString(uuid)).orElseThrow(CoverNotFoundException::new);
    }

    public Cover saveCover(Cover cover) {
        return coverRepository.save(cover);
    }
}
