package org.korocheteam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.StreamStatusDto;
import org.korocheteam.api.services.AudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final AudioService audioService;

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getGenres() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Collections.singletonMap("genres", audioService.getGenres()));
    }

    @GetMapping("/{genre}")
    public ResponseEntity<StreamStatusDto> getStatus(@PathVariable("genre") String genre) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(audioService.getStreamStatus(genre));
    }
}
