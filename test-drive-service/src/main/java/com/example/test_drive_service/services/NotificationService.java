package com.example.test_drive_service.services;

import com.example.common.dtos.NotificationRequestDTO;
import com.example.common.dtos.NotificationResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Slf4j
public class NotificationService {

    public NotificationResponseDTO sendNotification(NotificationRequestDTO request) {
        log.info("Sending notification: {}", request.getMessage());

        return NotificationResponseDTO.builder()
                .id(1L)
                .message(request.getMessage())
                .type(request.getType())
                .timestamp(LocalDateTime.now())
                .sent(true)
                .build();
    }

    public NotificationResponseDTO sendPromotionalNotification(NotificationRequestDTO request) {
        request.getPhoneNumbers().forEach(phone ->
                log.info("Sending promotional message to {}: {}", phone, request.getMessage()));

        return NotificationResponseDTO.builder()
                .id(1L)
                .message(request.getMessage())
                .type("PROMO")
                .timestamp(LocalDateTime.now())
                .sent(true)
                .build();
    }

    public NotificationResponseDTO sendViolationAlert(Long employeeId, Long testDriveId, String violationType) {
        String message = String.format(
                "ALERT: Test drive %d has violated %s limits. Return to dealership immediately.",
                testDriveId,
                violationType);

        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setMessage(message);
        request.setType("VIOLATION");
        request.setPhoneNumbers(Collections.emptyList());

        return sendNotification(request);
    }
}