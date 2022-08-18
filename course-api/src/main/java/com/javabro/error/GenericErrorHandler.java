package com.javabro.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GenericErrorHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> noHandlerFoundExceptionHandler(HttpServletResponse response) {
        System.out.println("called noHandlerFoundExceptionHandler");
        ErrorResponse serverResponse = new ErrorResponse();
        serverResponse.setDateTime(LocalDateTime.now());
        serverResponse.setMessage("no resource found for your search");
        serverResponse.setStatus(404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serverResponse);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> RecordNotFoundException(Exception ex, WebRequest webRequest) {
        System.out.println("called noSuchElementExceptionHandler");
        ErrorResponse serverResponse = new ErrorResponse();
        serverResponse.setDateTime(LocalDateTime.now());
        serverResponse.setMessage(ex.getMessage());
        serverResponse.setStatus(404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serverResponse);
    }

}
