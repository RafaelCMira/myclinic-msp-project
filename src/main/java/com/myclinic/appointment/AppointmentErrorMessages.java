package com.myclinic.appointment;

public enum AppointmentErrorMessages {
    APPOINTMENT_NOT_FOUND("Appointment not found"),

    INSERTING_APPOINTMENT_DB_ERROR("Error inserting Appointment in the database"),
    APPOINTMENT_ALREADY_EXISTS("Appointment already exists in the database");

    private final String message;

    AppointmentErrorMessages(String message) {
        this.message = message;
    }

    public String formatMsg(Object param) {
        return this.message + ": " + param;
    }
}
