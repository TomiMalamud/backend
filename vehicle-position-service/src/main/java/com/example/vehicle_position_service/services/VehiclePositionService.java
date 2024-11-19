package com.example.vehicle_position_service.services;

import com.example.common.dtos.DealershipConfigDTO;
import com.example.common.dtos.VehiclePositionDTO;
import com.example.common.entities.Position;
import com.example.common.entities.Vehicle;
import com.example.common.entities.TestDrive;
import com.example.vehicle_position_service.repositories.PositionRepository;
import com.example.vehicle_position_service.repositories.TestDriveRepository;
import com.example.vehicle_position_service.services.external.DealershipConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VehiclePositionService {
    private final PositionRepository positionRepository;
    private final TestDriveRepository testDriveRepository;
    private final DealershipConfigService dealershipConfigService;
    private final NotificationService notificationService;

    public void processPosition(VehiclePositionDTO positionDTO) {
        Position position = savePosition(positionDTO);

        Optional<TestDrive> activeTestDrive = testDriveRepository
                .findByVehiculo_IdAndFechaHoraFinIsNull(positionDTO.getVehicleId());

        if (activeTestDrive.isPresent()) {
            checkPositionConstraints(positionDTO, activeTestDrive.get());
        }
    }

    private Position savePosition(VehiclePositionDTO positionDTO) {
        Position position = new Position();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(positionDTO.getVehicleId());
        position.setVehiculo(vehicle);
        position.setLatitud(positionDTO.getLatitude());
        position.setLongitud(positionDTO.getLongitude());
        position.setFechaHora(positionDTO.getTimestamp());
        return positionRepository.save(position);
    }

    private void checkPositionConstraints(VehiclePositionDTO position, TestDrive testDrive) {
        DealershipConfigDTO config = dealershipConfigService.getConfig();

        if (isOutsideRadius(position, config)) {
            notifyViolation(testDrive, "RADIUS", position);
            restrictCustomer(testDrive);
        }

        if (isInDangerZone(position, config)) {
            notifyViolation(testDrive, "DANGER_ZONE", position);
            restrictCustomer(testDrive);
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
        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }

    private void notifyViolation(TestDrive testDrive, String violationType, VehiclePositionDTO position) {
        String location = String.format("(%.6f, %.6f)", position.getLatitude(), position.getLongitude());
        notificationService.sendViolationAlert(
                testDrive.getEmpleado().getLegajo(),
                testDrive.getId(),
                violationType + " at " + location
        );
    }

    private void restrictCustomer(TestDrive testDrive) {
        testDrive.getInteresado().setRestringido(true);
        // Note: You'll need to save this through a repository
    }

    public VehiclePositionDTO getLastPosition(Long vehicleId) {
        Position position = positionRepository.findFirstByVehiculo_IdOrderByFechaHoraDesc(vehicleId)
                .orElseThrow(() -> new RuntimeException("No positions found for vehicle"));

        return convertToDTO(position);
    }


    public List<VehiclePositionDTO> getPositionTrack(Long vehicleId, LocalDateTime start, LocalDateTime end) {
        return positionRepository.findByVehiculo_IdAndFechaHoraBetweenOrderByFechaHoraAsc(
                        vehicleId,
                        start,
                        end
                )
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