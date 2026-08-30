package scesi.org.check.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.user.model.exceptions.RolUserAlreadyExistException;
import scesi.org.check.user.model.exceptions.RolUserNotFoundException;

@ControllerAdvice
public class RolUserExceptionHandler {
    @ExceptionHandler(RolUserAlreadyExistException.class)
    public ResponseEntity<StandardResponse<Object>> handleRolUserAlreadyExistException(RolUserAlreadyExistException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RolUserNotFoundException.class)
    public ResponseEntity<StandardResponse<Object>> handleRolUserNotFoundException(RolUserNotFoundException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
