package com.myclinic.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResponse<T> {
    public enum Status {
        SUCCESS,
        FAILED
    }

    private String status;

    private T result;

    private ErrorDTO error;

}