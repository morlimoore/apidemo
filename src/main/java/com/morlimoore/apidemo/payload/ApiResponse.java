package com.morlimoore.apidemo.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.morlimoore.apidemo.entities.User;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiResponse {

    private HttpStatus status;
    private String message;
    private String error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String debugMessage;
    private List<User> data;

    public ApiResponse() {
        timestamp = LocalDateTime.now();
    }

    public ApiResponse(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiResponse(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.error = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiResponse(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}