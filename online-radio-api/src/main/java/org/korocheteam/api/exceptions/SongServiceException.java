package org.korocheteam.api.exceptions;

public class SongServiceException extends RuntimeException {
    public SongServiceException() {
    }

    public SongServiceException(String message) {
        super(message);
    }

    public SongServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SongServiceException(Throwable cause) {
        super(cause);
    }

    public SongServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
