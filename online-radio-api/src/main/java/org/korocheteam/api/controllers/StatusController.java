package org.korocheteam.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.StreamStatusDto;
import org.korocheteam.api.models.dtos.responses.GenresResponse;
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
@Api("endpoint for getting genres and statuses for these genres")
public class StatusController {

    private final AudioService audioService;

    @ApiOperation("returns all genres(must contain token in header)")
    @ApiResponse(code = 200, message = "returns list of genres", response = GenresResponse.class)
    @GetMapping
    public ResponseEntity<GenresResponse> getGenres() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GenresResponse.builder().genres(audioService.getGenres()).build());
    }

    @ApiOperation("returns status of stream of specified genre(must contain token in header)")
    @ApiResponse(code = 200, message = "returns status of specified genre", response = StreamStatusDto.class)
    @ApiImplicitParam(name = "genre", required = true, dataType = "String",
            paramType = "query", value = "param to get status of stream of specified genre")
    @GetMapping("/{genre}")
    public ResponseEntity<StreamStatusDto> getStatus(@PathVariable("genre") String genre) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(audioService.getStreamStatus(genre));
    }
}
