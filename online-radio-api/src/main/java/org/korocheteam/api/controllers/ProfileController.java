package org.korocheteam.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
@Api("endpoint for getting profile and updating")
public class ProfileController {

	private final AccountService accountService;

	@ApiOperation("returns current user profile(must contain token in header)")
	@ApiResponse(code = 200, message = "returns current user profile", response = ProfileResponse.class)
	@GetMapping
	public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
		return ResponseEntity.ok(accountService.getProfileByEmail(authentication.getName()));
	}

	@ApiOperation("returns specified profile(must contain token in header)")
	@ApiResponse(code = 200, message = "returns profile by nickname", response = ProfileResponse.class)
	@GetMapping("/{nickname}")
	public ResponseEntity<ProfileResponse> getProfile(@PathVariable String nickname) {
		return ResponseEntity.ok(accountService.getProfileByNickname(nickname));
	}

	@ApiOperation("update specified profile(must contain token in header)")
	@ApiResponse(code = 202, message = "returns updated profile", response = AccountDto.class)
	@PutMapping
	public ResponseEntity<ProfileResponse> updateProfile(Authentication authentication, @Valid @RequestBody ProfileRequest profileRequest) {
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(accountService.updateProfile(authentication.getName(), profileRequest));
	}
}
