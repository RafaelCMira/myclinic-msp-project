package com.myclinic.doctor;

enum DoctorErrorMessages {
    DOCTOR_NOT_FOUND("Doctor not found"),

    INSERTING_DOCTOR_DB_ERROR("Error inserting doctor in the database");

    private final String message;

    DoctorErrorMessages(String message) {
        this.message = message;
    }

    String formatMsg(Object param) {
        return this.message + ": " + param;
    }
}
