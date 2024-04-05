package com.myclinic.exception;


import java.time.LocalDateTime;

record ApiError(
        String path,
        String message,
        int statusCode,
        LocalDateTime timestamp
) {
}
