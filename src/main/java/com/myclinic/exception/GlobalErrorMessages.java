package com.myclinic.exception;

public enum GlobalErrorMessages {

    NULL_PARAM("Null mandatory parameter"),
    BLANCK_PARAM("Mandatory parameter blank"),
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
