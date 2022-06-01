package org.korocheteam.api.exceptions;

public class GenreNotFoundException extends RuntimeException{
	public GenreNotFoundException(String genre) {
		super("genre " + genre + " not exists");
	}
}
