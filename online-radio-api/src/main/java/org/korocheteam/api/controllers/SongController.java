package org.korocheteam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.responses.TopSongsResponse;
import org.korocheteam.api.services.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongController {

	private final SongService songService;

	@GetMapping("/top")
	public ResponseEntity<TopSongsResponse> getTopTenSongs() {
		return ResponseEntity.ok(songService.getTopTenSongs());
	}
}
