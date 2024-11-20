package com.example.test_drive_service.repositories;

import com.example.common.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEmpleadoLegajo(Long Legajo);  // Changed from findByEmployeeId
    List<Notification> findByFechaHoraBetween(LocalDateTime start, LocalDateTime end);  // Changed from findByTimestampBetween
    // Remove findByType as there's no 'type' field in your entity
}