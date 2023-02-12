package tech.xavi.exchangeapi.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.xavi.exchangeapi.configuration.ExchangeApiException;
import tech.xavi.exchangeapi.dto.rest.exception.ApiExceptionDTO;
import tech.xavi.exchangeapi.model.ExchangeError;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler{

    @ExceptionHandler(ExchangeApiException.class)
    public ResponseEntity<ApiExceptionDTO> handleApiException(ExchangeApiException exception){
        return new ResponseEntity<>(
                ApiExceptionDTO
                        .builder()
                        .errorCode(exception.getExchangeApiError().getErrorCode())
                        .errorString(exception.getExchangeApiError().name())
                        .message(exception.getExchangeApiError().getMessage())
                        .moment(LocalDateTime.now())
                        .build(),
                exception.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
    })
    public ResponseEntity<ApiExceptionDTO> methodNotAllowedException(){
        return new ResponseEntity<>(
                ApiExceptionDTO
                        .builder()
                        .errorCode(ExchangeError.METHOD_NOT_ALLOWED.getErrorCode())
                        .errorString(ExchangeError.METHOD_NOT_ALLOWED.name())
                        .message(ExchangeError.METHOD_NOT_ALLOWED.getMessage())
                        .moment(LocalDateTime.now())
                        .build(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
    })
    public ResponseEntity<ApiExceptionDTO> messageNotReadableException(){
        return new ResponseEntity<>(
                ApiExceptionDTO
                        .builder()
                        .errorCode(ExchangeError.BAD_REQUEST.getErrorCode())
                        .errorString(ExchangeError.BAD_REQUEST.name())
                        .message(ExchangeError.BAD_REQUEST.getMessage())
                        .moment(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

}
