package com.example.test_drive_service.services;

import com.example.common.dtos.NotificationRequestDTO;
import com.example.common.dtos.NotificationResponseDTO;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Service
@Slf4j
public class NotificationService {

    public NotificationResponseDTO sendNotification(NotificationRequestDTO request) {
        // Log the notification
        log.info("Sending notification to employee {}: {}",
                request.getEmployeeId(),
                request.getMessage());

        // Build response
        return NotificationResponseDTO.builder()
                .id(1L)  // In a real app, this would be from database
                .message(request.getMessage())
                .timestamp(LocalDateTime.now())
                .status("SENT")
                .build();
    }

    public NotificationResponseDTO sendPromotionalNotification(NotificationRequestDTO request) {
        // Log promotional notification
        request.getPhoneNumbers().forEach(phone ->
                log.info("Sending promotional message to {}: {}", phone, request.getMessage()));

        // Build response
        return NotificationResponseDTO.builder()
                .id(1L)  // In a real app, this would be from database
                .message(request.getMessage())
                .timestamp(LocalDateTime.now())
                .status("SENT")
                .build();
    }

    public NotificationResponseDTO sendViolationAlert(Long employeeId, Long testDriveId, String violationType) {
        String message = String.format(
                "ALERT: Test drive %d has violated %s limits. Return to dealership immediately.",
                testDriveId,
                violationType);

        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setEmployeeId(employeeId);
        request.setMessage(message);

        return sendNotification(request);
    }
}