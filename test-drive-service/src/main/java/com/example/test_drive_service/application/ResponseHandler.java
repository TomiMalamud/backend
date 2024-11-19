package com.example.test_drive_service.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<?> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<?> error(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (e instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        }
        // Add more exception types if needed

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<?> success(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}