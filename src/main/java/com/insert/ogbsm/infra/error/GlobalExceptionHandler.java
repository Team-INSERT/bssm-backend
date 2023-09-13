package com.insert.ogbsm.infra.error;

import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final String errorLogsFormat = """
                        
            {
                      "status": "%s",
                      "code": "%s",
                      "message": "%s"
            }
            """;

    @ExceptionHandler(BsmException.class)
    public ResponseEntity<ErrorResponse> handleGlobal(BsmException e) {
        final ErrorCode errorCode = e.getErrorCode();
        log.error(
                errorLogsFormat.formatted(
                        errorCode.getStatus(),
                        errorCode.getCode(),
                        errorCode.getMessage()
                )
        );
        return new ResponseEntity<>(
                new ErrorResponse(
                        errorCode.getStatus(),
                        errorCode.getCode(),
                        errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getStatus())
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersistenceException(EntityNotFoundException e) {
        final ErrorCode errorCode = ErrorCode.NOT_FOUND;
        log.error(
                errorLogsFormat.formatted(
                        errorCode.getStatus(),
                        errorCode.getCode(),
                        errorCode.getMessage()
                )
        );
        return new ResponseEntity<>(
                new ErrorResponse(
                        errorCode.getStatus(),
                        errorCode.getCode(),
                        errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getStatus())
        );
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<?> bindException(BindException e) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : e.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
            log.error(
                    errorLogsFormat.formatted(
                            HttpStatus.BAD_REQUEST.value(),
                            "400",
                            errorMap.toString()
                    )
            );
        }
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
        Map<String, String> errorMap = new HashMap<>();

        for (ConstraintViolation<?> error : e.getConstraintViolations()) {
            errorMap.put("constraint error", error.getMessage());
            log.error(error.toString());
        }

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();

        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        errorMap.put("validation error", errorMessage);

        log.error(errorMessage);
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
