package org.korocheteam.api.exceptions;

public class SongAlreadyLiked extends RuntimeException {
	public SongAlreadyLiked() {
		super("song already liked");
	}
}
