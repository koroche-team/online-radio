package org.korocheteam.api.exceptions;

public class CoverNotFoundException extends RuntimeException {
    public CoverNotFoundException() {
        super();
    }

    public CoverNotFoundException(String message) {
        super(message);
    }

    public CoverNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoverNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CoverNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
