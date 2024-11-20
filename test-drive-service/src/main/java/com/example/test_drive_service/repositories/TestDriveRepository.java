package com.example.test_drive_service.repositories;

import com.example.common.entities.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestDriveRepository extends JpaRepository<TestDrive, Long> {
    List<TestDrive> findByFechaHoraFinIsNull();
    List<TestDrive> findByVehiculo_IdAndFechaHoraFinIsNull(Long vehicleId);

    // Remove or modify these methods since hasViolations no longer exists
    // List<TestDrive> findByHasViolationsTrue();
    // List<TestDrive> findByEmpleado_LegajoAndHasViolationsTrue(Long employeeId);

    // Replace with simple queries
    List<TestDrive> findByEmpleado_Legajo(Long employeeId);
    List<TestDrive> findByVehiculo_Id(Long vehicleId);
}