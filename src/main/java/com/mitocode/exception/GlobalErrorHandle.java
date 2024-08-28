package com.mitocode.exception;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalErrorHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity <CustomErrorResponseRecord> handleAllException(Exception ex, WebRequest req){
        CustomErrorResponseRecord errorResponse = new CustomErrorResponseRecord(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
        return  new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorResponseRecord> handleModelNotFoundException(ModelNotFoundException ex, WebRequest req){
        CustomErrorResponseRecord errorResponse = new CustomErrorResponseRecord(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
       }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<CustomErrorResponse>handleSQLException(SQLException ex, WebRequest req){
        CustomErrorResponse errorResponse =new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message =ex.getBindingResult().getFieldErrors().stream()
                .map(error-> error.getField() +": " +error.getDefaultMessage())
                .collect(Collectors.joining(","));

                  /* for(FieldError error : ex.getBindingResult().getFieldErrors()){
                       message += error.getField() + ": "+error.getDefaultMessage();
                   }*/

        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), message, request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
    //Desde SpringBoot 3
   /* @ExceptionHandler(ModelNotFoundException.class)
    public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex, WebRequest req){
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Model Not Found");
        pd.setType(URI.create(req.getContextPath()));

        pd.setProperty("var1", "value");
        pd.setProperty("var2", true);
        pd.setProperty("var3", 32);
        return pd;



    }
    }*/





