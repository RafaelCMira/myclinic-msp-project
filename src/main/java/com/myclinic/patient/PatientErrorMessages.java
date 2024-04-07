package com.myclinic.patient;

enum PatientErrorMessages {
    PATIENT_NOT_FOUND("Patient not found"),

    INSERTING_PATIENT_DB_ERROR("Error inserting patient in the database");

    private final String message;

    PatientErrorMessages(String message) {
        this.message = message;
    }

    String formatMsg(Object param) {
        return this.message + ": " + param;
    }
}
