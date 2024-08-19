package org.example.exceptions;

public class TriangleDoesNotExistException extends Exception {
    public TriangleDoesNotExistException() {
        super("Triangle with given sides lengths does not exist");
    }
    public TriangleDoesNotExistException(String message) {
        super(message);
    }

    public TriangleDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
