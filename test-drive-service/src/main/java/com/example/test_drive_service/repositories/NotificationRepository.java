package com.example.test_drive_service.repositories;

import com.example.common.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEmployeeId(Long employeeId);
    List<Notification> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Notification> findByType(String type);
}