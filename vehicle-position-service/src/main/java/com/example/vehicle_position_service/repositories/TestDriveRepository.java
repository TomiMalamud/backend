package com.example.vehicle_position_service.repositories;

import com.example.common.entities.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TestDriveRepository extends JpaRepository<TestDrive, Long> {
    Optional<TestDrive> findByVehiculo_IdAndFechaHoraFinIsNull(Long vehicleId);
}