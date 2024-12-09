package org.example.exceptions;

public class SendMessageException extends RuntimeException {
    public SendMessageException(String message) {
        super(message);
    }

    public SendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
