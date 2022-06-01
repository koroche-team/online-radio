package org.korocheteam.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.requests.SignUpRequest;
import org.korocheteam.api.models.dtos.responses.SignUpResponse;
import org.korocheteam.api.services.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("sign up")
@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

	private final SignUpService signUpService;

	@ApiResponse(code = 201, message = "user successfully signed up", response = SignUpResponse.class)
	@PostMapping
	public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(signUpService.signUp(request));
	}
}
