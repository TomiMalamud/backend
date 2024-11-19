package com.example.test_drive_service.repositories;

import com.example.common.entities.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TestDriveRepository extends JpaRepository<TestDrive, Long> {
    List<TestDrive> findByFechaHoraFinIsNull();
    boolean existsByVehicleIdAndFechaHoraFinIsNull(Long vehicleId);
    List<TestDrive> findByVehicleId(Long vehicleId);
    List<TestDrive> findByEmployeeId(Long employeeId);
    List<TestDrive> findByFechaHoraInicioBetween(LocalDateTime start, LocalDateTime end);
    List<TestDrive> findByInterestedId(Long interestedId);
}