package org.korocheteam.api.exceptions;

public class AccountNotExistsException extends RuntimeException{
	public AccountNotExistsException(String message) {
		super(message);
	}
}
