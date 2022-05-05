package com.javamaster.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorDto exception(final Exception e) {
        log.error(e.getMessage(), e);
        return new ApiErrorDto("Error! " + e.getMessage());
    }

    @ExceptionHandler(ClientErrorException.class)
    public ApiErrorDto clientErrorException(final HttpServletResponse response, final ClientErrorException e) {
        response.setStatus(e.getResponse().getStatus());
        return new ApiErrorDto(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto fieldArgumentNotValidException(final BindException e) {
        final List<ErrorFieldDto> errorFields = e.getBindingResult().getFieldErrors().stream()
                .map(x -> new ErrorFieldDto(x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ApiErrorDto("Error. Please check your entry and try again.", errorFields);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto maxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return new ApiErrorDto("Error. Please check your entry and try again.",
                List.of(new ErrorFieldDto("attachment", "Maximum upload size exceeded.")));
    }
}
