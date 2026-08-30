package scesi.org.check.rol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.rol.model.exception.RolAlreadyExist;
import scesi.org.check.rol.model.exception.RolNotFoundException;

@ControllerAdvice
public class RolExceptionHandler {
    @ExceptionHandler(RolAlreadyExist.class)
    public ResponseEntity<StandardResponse<Object>> handleRolAlreadyExist(RolAlreadyExist ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RolNotFoundException.class)
    public ResponseEntity<StandardResponse<Object>> handleRolNotFoundException(RolNotFoundException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
