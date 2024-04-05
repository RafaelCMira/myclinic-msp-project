package com.myclinic.exception;

import com.myclinic.exception.customexceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
class APiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<Object> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Object> handleResourceNotFoundException(NotFoundException e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<Object> handleException(Exception e, HttpServletRequest request, HttpStatus status) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                status.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, status);
    }
}
