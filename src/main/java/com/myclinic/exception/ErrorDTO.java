package com.myclinic.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
record ErrorDTO(
        String path,
        int httpCode,
        String timestamp,
        List<Error> errors,
        String message
) {
}

