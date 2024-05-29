package com.myclinic.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utility {

    private Utility() {

    }

    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    public static Integer getIntOrNull(ResultSet rs, String columnName) {
        try {
            return rs.getInt(columnName);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static Double getDoubleOrNull(ResultSet rs, String columnName) {
        try {
            return rs.getDouble(columnName);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static String getStringOrNull(ResultSet rs, String columnName) {
        try {
            return rs.getString(columnName);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static LocalDate getLocalDateOrNull(ResultSet rs, String columnName) {
        try {
            return rs.getDate(columnName).toLocalDate();
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static LocalTime getLocalTimeOrNull(ResultSet rs, String columnName) {
        try {
            return rs.getTime(columnName).toLocalTime();
        } catch (SQLException ignored) {
            return null;
        }
    }
}
