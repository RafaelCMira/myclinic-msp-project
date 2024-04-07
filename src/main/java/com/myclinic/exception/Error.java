package com.myclinic.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String field;
    private String input;
    private String errorMessage;
}
