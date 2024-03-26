package com.ecommerce.portal.handlers;

import com.ecommerce.portal.dtos.ResponseErrorDTO;
import com.ecommerce.portal.exceptions.OrderException;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.exceptions.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class UserServiceAPIHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDTO> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.info("Entered into UserAPIHandler class with exception : "+ exception.getMessage());
        return new ResponseEntity<ResponseErrorDTO>(ResponseErrorDTO.builder()
                .error_messages(exception.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList())
//                .exception_message(exception.getMessage())
                .statusCodeDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseErrorDTO> userException(UserException exception){
        log.info("Entered into userException class with exception : "+ exception.getMessage());
        return new ResponseEntity<ResponseErrorDTO>(ResponseErrorDTO.builder()
                .exception_message(exception.getMessage())
                .statusCodeDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ResponseErrorDTO> productException(ProductException exception){
        log.info("Entered into userException class with exception : "+ exception.getMessage());
        return new ResponseEntity<ResponseErrorDTO>(ResponseErrorDTO.builder()
                .exception_message(exception.getMessage())
                .statusCodeDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ResponseErrorDTO> orderException(OrderException exception){
        log.info("Entered into userException class with exception : "+ exception.getMessage());
        return new ResponseEntity<ResponseErrorDTO>(ResponseErrorDTO.builder()
                .exception_message(exception.getMessage())
                .statusCodeDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }
}
