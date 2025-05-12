package com.example.email_validation.handler;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(LockedException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponseDto.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                                .error(ex.getMessage())
                                .build());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(DisabledException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponseDto.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .error(ex.getMessage())
                                .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(BadCredentialsException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponseDto.builder()
                                .businessErrorCode(BusinessErrorCodes.BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .error(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .build());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(MessagingException ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponseDto.builder()
                                .error(ex.getMessage())
                                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(MethodArgumentNotValidException ex){
        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponseDto.builder()
                                .validationErrors(errors)
                                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleException(Exception ex){
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponseDto.builder()
                                .businessErrorDescription("Internal error, contact the admin")
                                .error(ex.getMessage())
                                .build());
    }

}
