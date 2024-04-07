package com.myclinic.exception;

import com.myclinic.exception.customexceptions.NotFoundException;
import com.myclinic.exception.customexceptions.SqlInjectionException;
import com.myclinic.exception.customexceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class DoctorServiceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<Error> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    var value = error.getRejectedValue() == null ? "null" : error.getRejectedValue().toString();
                    Error err = new Error(error.getField(), value, error.getDefaultMessage());
                    errors.add(err);
                });

        return handleException(request, HttpStatus.BAD_REQUEST, null, errors);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(NotFoundException e, HttpServletRequest request) {
        return handleException(request, HttpStatus.NOT_FOUND, e.getMessage(), null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<ApiResponse<?>> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
        return handleException(request, HttpStatus.UNAUTHORIZED, e.getMessage(), null);
    }

    @ExceptionHandler(SqlInjectionException.class)
    ResponseEntity<ApiResponse<?>> handleSqlInjectionException(SqlInjectionException e, HttpServletRequest request) {
        return handleException(request, HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    private ResponseEntity<ApiResponse<?>> handleException(HttpServletRequest request, HttpStatus status, String message, List<Error> errors) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        ErrorDTO errorDTO = new ErrorDTO(
                request.getRequestURI(),
                status.value(),
                formattedDateTime,
                errors,
                message
        );

        ApiResponse<?> apiResponse = new ApiResponse<>(
                ApiResponse.Status.FAILED.name(),
                null,
                errorDTO
        );

        return new ResponseEntity<>(apiResponse, status);
    }
}
