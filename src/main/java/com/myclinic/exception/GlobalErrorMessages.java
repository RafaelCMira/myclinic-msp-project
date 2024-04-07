package com.myclinic.exception;

public enum GlobalErrorMessages {
    
    SQL_INJECTION("SQL Injection detected");

    private final String message;

    GlobalErrorMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

    public String formatMsg(Object param) {
        return this.message + ": " + param;
    }

}
