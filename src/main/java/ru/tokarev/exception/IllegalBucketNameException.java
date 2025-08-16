package ru.tokarev.exception;

public class IllegalBucketNameException extends RuntimeException {
    public IllegalBucketNameException(String message) {
        super(message);
    }
}
