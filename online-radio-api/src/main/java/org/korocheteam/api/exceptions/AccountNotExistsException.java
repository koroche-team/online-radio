package org.korocheteam.api.exceptions;

public class AccountNotExistsException extends RuntimeException{
	public AccountNotExistsException(String message) {
		super(message);
	}

	public AccountNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotExistsException(Throwable cause) {
		super(cause);
	}

	public AccountNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountNotExistsException() {
	}
}
