package com.example.vehicle_position_service.repositories;

import com.example.common.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findFirstByIdVehiculoOrderByFechaHoraDesc(Long idVehiculo);
    List<Position> findByIdVehiculoAndFechaHoraBetweenOrderByFechaHoraAsc(
            Long idVehiculo, String startTime, String endTime);
}