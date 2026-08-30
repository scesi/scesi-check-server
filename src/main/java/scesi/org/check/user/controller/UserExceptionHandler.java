package scesi.org.check.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.user.model.exceptions.UserEmailAlreadyExistException;
import scesi.org.check.user.model.exceptions.UserNotFoundException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardResponse<Object>> handleUserNotFoundException(UserNotFoundException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserEmailAlreadyExistException.class)
    public ResponseEntity<StandardResponse<Object>> handleUserEmailAlreadyExist(UserEmailAlreadyExistException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
