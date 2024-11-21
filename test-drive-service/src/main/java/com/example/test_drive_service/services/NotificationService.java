package com.example.test_drive_service.services;

import com.example.common.dtos.NotificationRequestDTO;
import com.example.common.dtos.NotificationResponseDTO;
import com.example.common.entities.Notification;
import com.example.test_drive_service.exceptions.ResourceNotFoundException;
import com.example.test_drive_service.repositories.EmployeeRepository;
import com.example.test_drive_service.repositories.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               EmployeeRepository employeeRepository) {
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
    }

    public NotificationResponseDTO sendNotification(NotificationRequestDTO request) {
        log.info("Sending notification: {}", request.getMessage());

        Notification notification = new Notification();
        notification.setMensaje(request.getMessage());
        notification.setFechaHora(LocalDateTime.now());

        if (request.getEmployeeId() != null) {
            notification.setEmpleado(employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));
        }

        return createResponseDTO(notificationRepository.save(notification), request.getType());
    }

    public NotificationResponseDTO sendPromotionalNotification(NotificationRequestDTO request) {
        request.getPhoneNumbers().forEach(phone ->
                log.info("Sending promotional message to {}: {}", phone, request.getMessage()));

        Notification notification = new Notification();
        notification.setMensaje(request.getMessage());
        notification.setFechaHora(LocalDateTime.now());

        return createResponseDTO(notificationRepository.save(notification), "PROMO");
    }

    public NotificationResponseDTO sendViolationAlert(Long employeeId, Long testDriveId, String violationType) {
        String message = String.format(
                "ALERT: Test drive %d has violated %s limits. Return to dealership immediately.",
                testDriveId,
                violationType);

        Notification notification = new Notification();
        notification.setMensaje(message);
        notification.setFechaHora(LocalDateTime.now());
        notification.setEmpleado(employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));

        return createResponseDTO(notificationRepository.save(notification), "VIOLATION");
    }

    private NotificationResponseDTO createResponseDTO(Notification notification, String type) {
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .message(notification.getMensaje())
                .type(type)
                .timestamp(notification.getFechaHora())
                .sent(true)
                .build();
    }
}