package com.example.vehicle_position_service.services;

import com.example.common.dtos.NotificationRequestDTO;
import com.example.common.dtos.NotificationResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Slf4j
public class NotificationService {

    public NotificationResponseDTO sendViolationAlert(Long employeeId, Long testDriveId, String details) {
        String message = String.format(
                "ALERT: Test drive %d has violated limits. %s. Return to dealership immediately.",
                testDriveId,
                details
        );

        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setMessage(message);
        request.setType("VIOLATION");
        request.setPhoneNumbers(Collections.emptyList());

        return sendNotification(request);
    }

    private NotificationResponseDTO sendNotification(NotificationRequestDTO request) {
        log.info("Sending notification: {}", request.getMessage());

        return NotificationResponseDTO.builder()
                .id(1L)
                .message(request.getMessage())
                .type(request.getType())
                .timestamp(LocalDateTime.now())
                .sent(true)
                .build();
    }
}