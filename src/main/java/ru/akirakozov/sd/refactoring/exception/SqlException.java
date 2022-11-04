package ru.akirakozov.sd.refactoring.exception;

public class SqlException extends RuntimeException {
    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlException(String message) {
        super(message);
    }
}
