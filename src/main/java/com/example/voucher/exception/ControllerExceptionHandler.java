package com.example.voucher.exception;

import com.example.voucher.model.exception.ErrorMessage;
import com.example.voucher.model.exception.ValidationResults;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex) {
        logger.error(ex.getMessage());

        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "Internal Sever error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    public ErrorMessage globalExceptionHandler(Exception ex) {
        logger.error(ex.getMessage());

        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationResults.class)))
    Map<String, String> handleNotValidException(MethodArgumentNotValidException e) {
        ValidationResults validationResults = new ValidationResults();
        e.getBindingResult().getAllErrors()
                .stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .forEach(fieldError -> validationResults.addResult(fieldError.getField(), fieldError.getDefaultMessage()));
        return validationResults.getResults();
    }

    @ExceptionHandler(BusinessValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    public ErrorMessage handleBusinessValidationException(BusinessValidationException ex) {
        logger.error(ex.getMessage());

        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return message;
    }

}
