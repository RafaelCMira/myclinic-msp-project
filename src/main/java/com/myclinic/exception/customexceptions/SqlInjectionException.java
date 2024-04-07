package com.myclinic.exception.customexceptions;

public class SqlInjectionException extends RuntimeException {

    public SqlInjectionException(String message) {
        super(message);
    }

    public SqlInjectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
