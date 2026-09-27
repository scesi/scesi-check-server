package scesi.org.check.mqtt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.mqtt.model.exception.EnrollmentException;

@ControllerAdvice
public class EnrollmentExceptionHandler {

    @ExceptionHandler(EnrollmentException.class)
    public ResponseEntity<StandardResponse<Object>> handleEnrollmentException(EnrollmentException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}