package org.korocheteam.api.controllers;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

	private final LikeService likeService;

	@PostMapping
	public ResponseEntity<LikeSongResponse> likeSong(@RequestBody LikeSongRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(likeService.likeSong(request));
	}
}
