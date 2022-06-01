package org.korocheteam.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.responses.TopSongsResponse;
import org.korocheteam.api.services.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
@Api("endpoint for getting top songs")
public class SongController {

	private final SongService songService;

	@ApiOperation("returns top songs of specified genre(must contain token in header)")
	@ApiResponse(code = 200, message = "returns 10 top songs", response = TopSongsResponse.class)
	@ApiImplicitParam(name = "genre", required = true, dataType = "String",
			paramType = "query", value = "param to get top songs of specified genre")
	@GetMapping("/top/{genre}")
	public ResponseEntity<TopSongsResponse> getTopTenSongs(@PathVariable("genre") String genre) {
		return ResponseEntity.ok(songService.getTopTenSongs(genre));
	}
}
