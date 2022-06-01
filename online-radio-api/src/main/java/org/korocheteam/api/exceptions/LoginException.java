package org.korocheteam.api.exceptions;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {
	public LoginException() {
		super("email or password are incorrect");
	}
}
