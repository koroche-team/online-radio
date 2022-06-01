package org.korocheteam.api.exceptions;

public class AudioStreamException extends RuntimeException {
    public AudioStreamException() {
        super();
    }

    public AudioStreamException(String message) {
        super(message);
    }

    public AudioStreamException(String message, Throwable cause) {
        super(message, cause);
    }

    public AudioStreamException(Throwable cause) {
        super(cause);
    }

    protected AudioStreamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
