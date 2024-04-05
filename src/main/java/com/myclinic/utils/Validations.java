package com.myclinic.utils;

import com.myclinic.exception.GlobalErrorMessages;
import com.myclinic.exception.customexceptions.BadRequestException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Validations {

    private Validations() {
    }

    public static void validate(Object... params) {
        for (Object param : params) {
            checkNull(param);
            if (param instanceof String string) {
                checkBlanck(string);
                hasSqlInjection(string);
            }
        }
    }

    private static void checkNull(Object param) {
        if (param == null)
            throw new BadRequestException(GlobalErrorMessages.NULL_PARAM.message());
    }

    private static void checkBlanck(String string) {
        if (string.isBlank())
            throw new BadRequestException(GlobalErrorMessages.BLANCK_PARAM.message());
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
            throw new BadRequestException(GlobalErrorMessages.SQL_INJECTION.formatMsg(value));
    }


}
