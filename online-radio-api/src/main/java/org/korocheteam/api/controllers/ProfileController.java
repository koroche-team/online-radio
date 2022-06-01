package org.korocheteam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.dtos.AccountDto;
import org.korocheteam.api.models.dtos.requests.ProfileRequest;
import org.korocheteam.api.models.dtos.responses.ProfileResponse;
import org.korocheteam.api.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

	private final AccountService accountService;

	@GetMapping("/{nickname}")
	public ResponseEntity<ProfileResponse> getProfile(@PathVariable String nickname) {
		return ResponseEntity.ok(accountService.getProfileByNickname(nickname));
	}

	@PostMapping()
	public ResponseEntity<AccountDto> updateProfile(Authentication authentication, @Valid @RequestBody ProfileRequest profileRequest) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(accountService.updateProfile(authentication.getName(), profileRequest));
	}
}
