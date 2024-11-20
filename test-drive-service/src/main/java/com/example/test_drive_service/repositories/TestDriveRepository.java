package com.example.test_drive_service.repositories;

import com.example.common.entities.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestDriveRepository extends JpaRepository<TestDrive, Long> {
    List<TestDrive> findByVehicleIdAndFechaHoraFinIsNull(Long vehicleId);
    List<TestDrive> findByEmployeeId(Long employeeId);
    List<TestDrive> findByVehicleId(Long vehicleId);
    List<TestDrive> findByFechaHoraFinIsNull();
}