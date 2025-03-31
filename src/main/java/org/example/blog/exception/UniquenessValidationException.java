package org.example.blog.exception;

public class UniquenessValidationException extends RuntimeException {
    public UniquenessValidationException(String message) {
        super(message);
    }
}
