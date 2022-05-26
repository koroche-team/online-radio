package org.korocheteam.api.services;

import org.korocheteam.api.models.dtos.requests.SignUpRequest;
import org.korocheteam.api.models.dtos.responses.SignUpResponse;

public interface SignUpService {
	SignUpResponse signUp(SignUpRequest signupRequest);
}
