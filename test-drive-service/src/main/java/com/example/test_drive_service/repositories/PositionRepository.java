package com.example.test_drive_service.repositories;

import com.example.common.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    List<Position> findByIdVehiculoAndFechaHoraBetween(Long idVehiculo, String start, String end);

    List<Position> findByIdVehiculo(Long idVehiculo);
}
