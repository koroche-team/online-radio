package org.korocheteam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.StreamStatusDto;
import org.korocheteam.api.services.AudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.korocheteam.api.models.dtos.StreamStatusDto.from;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final AudioService audioService;

    @GetMapping
    public ResponseEntity<StreamStatusDto> getStatus() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(from(audioService.getStreamStatus()));
    }
}
