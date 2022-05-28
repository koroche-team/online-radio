package org.korocheteam.api.exceptions;

public class AudioServiceException extends RuntimeException {
    public AudioServiceException() {
        super();
    }

    public AudioServiceException(String message) {
        super(message);
    }

    public AudioServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AudioServiceException(Throwable cause) {
        super(cause);
    }

    protected AudioServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
