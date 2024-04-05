package com.myclinic.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Utility {

    private Utility() {

    }

    public static Integer getIntOrNull(ResultSet rs, String columnName) {
        try {
            return rs.getInt(columnName);
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
}
