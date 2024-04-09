package com.myclinic.exam;

enum ExamErrorMessages {
    EXAM_NOT_FOUND("Exam not found"),

    INSERTING_EXAM_DB_ERROR("Error inserting Exam in the database"),
    EXAM_ALREADY_EXISTS("Exam already exists in the database");

    private final String message;

    ExamErrorMessages(String message) {
        this.message = message;
    }

    String formatMsg(Object param) {
        return this.message + ": " + param;
    }
}
