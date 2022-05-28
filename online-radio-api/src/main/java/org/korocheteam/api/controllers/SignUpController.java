package org.korocheteam.api.controllers;

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

@RestController
@RequestMapping("/signUp")
@RequiredArgsConstructor
public class SignUpController {

	private final SignUpService signUpService;

	@PostMapping
	public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(signUpService.signUp(request));
	}
}
