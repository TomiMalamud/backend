package com.example.test_drive_service.application.controllers;

import com.example.common.dtos.NotificationRequestDTO;
import com.example.common.dtos.NotificationResponseDTO;
import com.example.test_drive_service.services.NotificationService;
import com.example.test_drive_service.application.ResponseHandler;
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
        try {
            NotificationResponseDTO response = notificationService.sendNotification(request);
            return ResponseHandler.success(response);
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @PostMapping("/promo")
    public ResponseEntity<?> sendPromotionalNotification(@RequestBody NotificationRequestDTO request) {
        try {
            NotificationResponseDTO response = notificationService.sendPromotionalNotification(request);
            return ResponseHandler.success(response);
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @PostMapping("/violation")
    public ResponseEntity<?> sendViolationAlert(
            @RequestParam Long employeeId,
            @RequestParam Long testDriveId,
            @RequestParam String violationType) {
        try {
            NotificationResponseDTO response =
                    notificationService.sendViolationAlert(employeeId, testDriveId, violationType);
            return ResponseHandler.success(response);
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }
}