package org.korocheteam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.responses.ProfileResponse;
import org.korocheteam.api.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

	private final AccountService accountService;

	@GetMapping("/{nickname}")
	public ResponseEntity<ProfileResponse> getProfile(@PathVariable String nickname) {
		return ResponseEntity.ok(accountService.getProfileByNickname(nickname));
	}
}
