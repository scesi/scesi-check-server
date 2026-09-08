package scesi.org.check.event.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.event.model.exception.EventAlreadyExistException;
import scesi.org.check.event.model.exception.EventNotFoundException;

@ControllerAdvice
public class EventExceptionHandler {
    @ExceptionHandler(EventAlreadyExistException.class)
    public ResponseEntity<StandardResponse<Object>> handleEventAlreadyExistException(EventAlreadyExistException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<StandardResponse<Object>> handleEventNotFoundException(EventNotFoundException ex) {
        StandardResponse<Object> response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
