package org.korocheteam.api.exceptions;

public class SongAlreadyLikedException extends RuntimeException {
	public SongAlreadyLikedException() {
		super("song already liked");
	}
}
