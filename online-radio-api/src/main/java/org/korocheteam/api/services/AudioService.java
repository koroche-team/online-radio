package org.korocheteam.api.services;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.entities.AudioStream;
import org.korocheteam.api.models.Genre;
import org.korocheteam.api.models.dtos.StreamStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.korocheteam.api.models.dtos.StreamStatusDto.from;

@Service
@RequiredArgsConstructor
public class AudioService {
    public final Map<Genre, AudioStream> streams;

    public List<String> getGenres() {
        return streams.keySet().stream()
                .map(Enum::toString)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public StreamStatusDto getStreamStatus(String genre) {
        return from(streams.get(Genre.valueOf(genre.toUpperCase())).getStreamStatus());
    }

    public void startStreams() {
        streams.values().forEach(AudioStream::updateMusicList);
        streams.values().forEach(AudioStream::runAudioStream);
    }
}
