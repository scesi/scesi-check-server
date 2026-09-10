package scesi.org.check.latefee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.latefee.model.exception.LateFeeNotFoundException;

@ControllerAdvice
public class LateFeeExceptionHandler {
    @ExceptionHandler(LateFeeNotFoundException.class)
    public ResponseEntity<StandardResponse<Object>> handleLateFeeNotFoundException(LateFeeNotFoundException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
