package com.morlimoore.apidemo.util;

import com.morlimoore.apidemo.entities.User;
import com.morlimoore.apidemo.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ResponseUtil {

    public static ResponseEntity<ApiResponse> createResponse(List<User> data, String message, String debugMessage, HttpStatus status, Throwable ex) {
        ApiResponse response = new ApiResponse(status);
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setData(data);
        response.setDebugMessage(debugMessage);
        response.setStatus(status);
        Optional<Throwable> opEx = Optional.ofNullable(ex);
        if(opEx.isPresent())
            response.setError(ex.getLocalizedMessage());

        return new ResponseEntity<>(response, status);
    }
}
