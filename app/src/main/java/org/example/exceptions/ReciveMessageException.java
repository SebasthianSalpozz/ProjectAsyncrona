package org.example.exceptions;

public class ReciveMessageException extends RuntimeException {
    public ReciveMessageException(String message) {
        super(message);
    }

    public ReciveMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
