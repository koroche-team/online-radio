package org.korocheteam.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.requests.LikeSongRequest;
import org.korocheteam.api.models.dtos.responses.LikeSongResponse;
import org.korocheteam.api.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("endpoint for like songs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

	private final LikeService likeService;

	@ApiOperation("like specified song and then return amount of likes(must contain token in header)")
	@ApiResponse(code = 201, message = "returns amount of likes", response = LikeSongResponse.class)
	@PostMapping
	public ResponseEntity<LikeSongResponse> likeSong(@RequestBody LikeSongRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(likeService.likeSong(request));
	}
}
