package com.chatapp.chatservice.web.controller;

import com.chatapp.chatservice.enums.FeignProps;
import com.chatapp.chatservice.utils.FeignExceptionUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.security.oauthbearer.internals.unsecured.OAuthBearerIllegalTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;


@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {SecurityException.class, AccessDeniedException.class})
    protected ResponseEntity<Object> handleSecurityException(Exception ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);

    }

    // when throwing an IllegalArgumentException, this usually means that current user current interaction
    // with the system is conflicting with records already in the database
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);

    }

    // usually this would mean that the client does not have the right to access the resource
    // due to missing or invalid credentials
    @ExceptionHandler(value = {OAuthBearerIllegalTokenException.class, OAuthBearerIllegalTokenException.class})
    protected ResponseEntity<Object> handleOAuthBearerIllegalTokenException(Exception ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);

    }

    //deal with errors when the element is not found
    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }

    // deal with exceptions thrown by Feign clients
    @ExceptionHandler(value = {FeignException.class})
    protected ResponseEntity<Object> handleFeignException(Exception ex, WebRequest request) {

        // parse the exception message to get the status code
        // each word is separated by a space and wrapped in []
        Map<FeignProps, String> feignProps = FeignExceptionUtils.getPropsFromFeignError(ex.getMessage());

        URI uri = URI.create(feignProps.get(FeignProps.PATH));

        // if status code cannot be found create a new with the status code

        // set the where the error occurred in the header
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return handleExceptionInternal(ex,
                feignProps.get(FeignProps.MESSAGE),
                headers,
                HttpStatus.valueOf(Integer.parseInt(feignProps.get(FeignProps.STATUS_CODE))),
                request);
    }

}
