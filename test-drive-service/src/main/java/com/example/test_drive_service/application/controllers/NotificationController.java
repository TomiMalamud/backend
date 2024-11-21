package com.example.test_drive_service.application.controllers;

import com.example.common.dtos.NotificationRequestDTO;
import com.example.common.dtos.NotificationResponseDTO;
import com.example.test_drive_service.application.ResponseHandler;
import com.example.test_drive_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/employee")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequestDTO request) {
        return handleRequest(() -> {
            NotificationResponseDTO response = notificationService.sendNotification(request);
            return ResponseHandler.success(response);
        });
    }

    @PostMapping("/promo")
    public ResponseEntity<?> sendPromotionalNotification(@RequestBody NotificationRequestDTO request) {
        return handleRequest(() -> {
            NotificationResponseDTO response = notificationService.sendPromotionalNotification(request);
            return ResponseHandler.success(response);
        });
    }

    @PostMapping("/violation")
    public ResponseEntity<?> sendViolationAlert(
            @RequestParam Long employeeId,
            @RequestParam Long testDriveId,
            @RequestParam String violationType) {
        return handleRequest(() -> {
            NotificationResponseDTO response =
                    notificationService.sendViolationAlert(employeeId, testDriveId, violationType);
            return ResponseHandler.success(response);
        });
    }

    // Utility method to centralize exception handling
    private ResponseEntity<?> handleRequest(RequestHandler handler) {
        try {
            return handler.handle();
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @FunctionalInterface
    private interface RequestHandler {
        ResponseEntity<?> handle() throws Exception;
    }
}
