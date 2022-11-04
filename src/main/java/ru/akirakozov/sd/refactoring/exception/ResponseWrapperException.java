package ru.akirakozov.sd.refactoring.exception;

public class ResponseWrapperException extends RuntimeException {
    public ResponseWrapperException(Throwable cause) {
        super("Response wrapper thrown exception", cause);
    }
}
