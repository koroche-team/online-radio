package org.korocheteam.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.requests.ScoreUpdateRequest;
import org.korocheteam.api.models.dtos.responses.ScoreUpdateResponse;
import org.korocheteam.api.services.ScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
@RequiredArgsConstructor
@Api("endpoint for updating score")
public class ScoreController {

	private final ScoreService scoreService;

	@ApiOperation("update user score(must contain token in header)")
	@ApiResponse(code = 202, message = "returns whole user data", response = ScoreUpdateResponse.class)
	@PutMapping
	public ResponseEntity<ScoreUpdateResponse> updateScore(@RequestBody ScoreUpdateRequest request) {
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(scoreService.updateScore(request));
	}
}
