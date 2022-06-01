package org.korocheteam.api.exceptions;

public class LoginException extends RuntimeException{
	public LoginException() {
		super("email or password are incorrect");
	}
}
