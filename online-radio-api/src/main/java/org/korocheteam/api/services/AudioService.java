package org.korocheteam.api.services;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.entities.AudioStream;
import org.korocheteam.api.exceptions.GenreNotFoundException;
import org.korocheteam.api.models.Genre;
import org.korocheteam.api.models.dtos.StreamStatusDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.korocheteam.api.models.dtos.StreamStatusDto.from;

@Service
@RequiredArgsConstructor
public class AudioService {
    public final Map<String, AudioStream> streams;

    public List<String> getGenres() {
        return new ArrayList<>(streams.keySet());
    }

    public StreamStatusDto getStreamStatus(String genre) {
        if (!streams.containsKey(genre)) {
            throw new GenreNotFoundException(genre);
        }

        return from(streams.get(genre).getStreamStatus());
    }

    public void startStreams() {
        streams.values().forEach(AudioStream::updateMusicList);
        streams.values().forEach(AudioStream::runAudioStream);
    }
}
