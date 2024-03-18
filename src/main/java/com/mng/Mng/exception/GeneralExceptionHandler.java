package com.mng.Mng.exception;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request
                                                                  ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ExceptionHandler(Exception ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }//
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> ExceptionHandler(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>(userNotFoundException.getMessage(),HttpStatus.BAD_REQUEST);
    }//
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> ExceptionHandler(InvalidInputException invalidInputException){
        return new ResponseEntity<>(invalidInputException.getMessage(),HttpStatus.BAD_REQUEST);
    }//
    @ExceptionHandler(OfficeNotFoundException.class)
    public ResponseEntity<?> ExceptionHandler(OfficeNotFoundException officeNotFoundException){
        return new ResponseEntity<>(officeNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<?> ExceptionHandler(LocationNotFoundException locationNotFoundException){
        return new ResponseEntity<>(locationNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }//
    @ExceptionHandler(ActionNotFoundException.class)
    public ResponseEntity<?> ExceptionHandler(ActionNotFoundException actionNotFoundException){
        return new ResponseEntity<>(actionNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }
}
