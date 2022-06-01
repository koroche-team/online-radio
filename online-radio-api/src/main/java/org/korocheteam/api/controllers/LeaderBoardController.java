package org.korocheteam.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.responses.LeaderboardResponse;
import org.korocheteam.api.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/leaderboard")
public class LeaderBoardController {

	private final AccountService accountService;

	@ApiOperation("returns leaderboard(must contain token in header)")
	@ApiResponse(code = 200, message = "sorted list of users", response = LeaderboardResponse.class)
	@GetMapping
	public ResponseEntity<LeaderboardResponse> getLeaderBoard() {
		return ResponseEntity.ok()
				.body(accountService.getAllAccountsSortedByScore());
	}
}
