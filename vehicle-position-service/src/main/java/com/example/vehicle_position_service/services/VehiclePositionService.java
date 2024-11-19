package com.example.vehicle_position_service.services;

import com.example.common.dtos.DealershipConfigDTO;
import com.example.common.dtos.VehiclePositionDTO;
import com.example.common.entities.Position;
import com.example.common.entities.Vehicle;
import com.example.vehicle_position_service.repositories.PositionRepository;
import com.example.vehicle_position_service.services.external.DealershipConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VehiclePositionService {
    private final PositionRepository positionRepository;
    private final DealershipConfigService dealershipConfigService;

    public void processPosition(VehiclePositionDTO positionDTO) {
        // Save the position
        Position position = new Position();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(positionDTO.getVehicleId());
        position.setVehiculo(vehicle);
        position.setLatitud(positionDTO.getLatitude());
        position.setLongitud(positionDTO.getLongitude());
        position.setFechaHora(positionDTO.getTimestamp());
        positionRepository.save(position);

        // Check if position violates any constraints
        checkPositionConstraints(positionDTO);
    }

    private void checkPositionConstraints(VehiclePositionDTO position) {
        DealershipConfigDTO config = dealershipConfigService.getConfig();

        // Check radius constraint
        if (isOutsideRadius(position, config)) {
            // Notify violation
            notifyViolation(position.getVehicleId(), "RADIUS");
        }

        // Check danger zones
        if (isInDangerZone(position, config)) {
            // Notify violation
            notifyViolation(position.getVehicleId(), "DANGER_ZONE");
        }
    }

    private boolean isOutsideRadius(VehiclePositionDTO position, DealershipConfigDTO config) {
        double distance = calculateDistance(
                position.getLatitude(), position.getLongitude(),
                config.getDealershipLatitude(), config.getDealershipLongitude()
        );
        return distance > config.getMaxRadius();
    }

    private boolean isInDangerZone(VehiclePositionDTO position, DealershipConfigDTO config) {
        return config.getDangerZones().stream().anyMatch(zone ->
                position.getLatitude() <= zone.getNwLatitude() &&
                        position.getLatitude() >= zone.getSeLatitude() &&
                        position.getLongitude() >= zone.getNwLongitude() &&
                        position.getLongitude() <= zone.getSeLongitude()
        );
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Using Euclidean distance as specified in requirements
        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }

    private void notifyViolation(Long vehicleId, String violationType) {
        // This would integrate with the notification service
        // For now, just log the violation
        log.warn("Vehicle {} has violated {} constraint", vehicleId, violationType);
    }

    public VehiclePositionDTO getLastPosition(Long vehicleId) {
        Position position = positionRepository.findTopByVehicleIdOrderByFechaHoraDesc(vehicleId)
                .orElseThrow(() -> new RuntimeException("No positions found for vehicle"));

        return convertToDTO(position);
    }

    public List<VehiclePositionDTO> getPositionTrack(Long vehicleId, LocalDateTime start, LocalDateTime end) {
        return positionRepository.findByVehicleIdAndFechaHoraBetweenOrderByFechaHora(vehicleId, start, end)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private VehiclePositionDTO convertToDTO(Position position) {
        VehiclePositionDTO dto = new VehiclePositionDTO();
        dto.setVehicleId(position.getVehiculo().getId());
        dto.setLatitude(position.getLatitud());
        dto.setLongitude(position.getLongitud());
        dto.setTimestamp(position.getFechaHora());
        return dto;
    }
}