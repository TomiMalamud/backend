package com.example.test_drive_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.common.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
