package com.myclinic.utils;

import com.myclinic.exception.GlobalErrorMessages;
import com.myclinic.exception.customexceptions.BadRequestException;
import com.myclinic.exception.customexceptions.SqlInjectionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Validations {

    private Validations() {
    }

    public static void validate(Object... params) {
        for (Object param : params)
            if (param instanceof String string)
                hasSqlInjection(string);
    }

    public static void validate(Record record) {
        for (RecordComponent component : record.getClass().getRecordComponents()) {
            try {
                Object value = component.getAccessor().invoke(record);
                if (value instanceof String string) {
                    hasSqlInjection(string);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Failed to access record component: " + component.getName(), e);
            }
        }
    }

    private static void hasSqlInjection(String value) {

        String[] commands = {
                "select", "insert", "update", "delete", "drop", "truncate", "replace", "handler", "load data",
                "create", "alter", "rename", "grant", "revoke", "lock", "unlock", "set", "commit", "rollback",
                "savepoint", "call", "prepare", "execute", "deallocate", "use", "show", "describe", "explain",
                "begin", "end", "delimiter", "@@", "\\/*", "\\*/", "-- ", "#", "/\\*!"
        };

        Set<String> distinctCommands = new HashSet<>(Arrays.asList(commands));
        String commandsPattern = "\\b(" + String.join("|", distinctCommands) + ")\\b|(;)";
        Pattern pattern = Pattern.compile(commandsPattern, Pattern.CASE_INSENSITIVE);

        Set<String> commentsSets = new HashSet<>(Arrays.asList("-- ", "/*", "*/", "#"));
        boolean hasComments = commentsSets.stream().anyMatch(value::contains);
        boolean hasSingleQuote = value.contains("'");
        boolean hasSemicolon = value.contains(";");

        boolean hasSqlInjection = (hasComments || hasSemicolon)
                && hasSingleQuote
                && pattern.matcher(value.toLowerCase()).find();

        if (hasSqlInjection)
            throw new SqlInjectionException(GlobalErrorMessages.SQL_INJECTION.formatMsg(value));


    }


}
