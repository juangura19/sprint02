package com.example.spring02.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    String mensaje = "";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarTodasException(RuntimeException ex, WebRequest request){
        ExceptionResponse exceptionResponse =  new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModeloNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> manejarModeloException(ModeloNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse =  new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        ex.getBindingResult().getAllErrors().forEach( e -> {
            mensaje += e.getDefaultMessage().toString() + "; ";
        });
        ExceptionResponse exceptionResponse =  new ExceptionResponse(LocalDateTime.now(),mensaje, request.getDescription(false));

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
