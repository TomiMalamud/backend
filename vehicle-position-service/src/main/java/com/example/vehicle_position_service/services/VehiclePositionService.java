package com.example.vehicle_position_service.services;

import com.example.common.dtos.DealershipConfigDTO;
import com.example.common.dtos.VehiclePositionDTO;
import com.example.common.entities.Position;
import com.example.common.entities.TestDrive;
import com.example.vehicle_position_service.repositories.PositionRepository;
import com.example.vehicle_position_service.repositories.TestDriveRepository;
import com.example.vehicle_position_service.services.external.DealershipConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                .findByVehicleIdAndFechaHoraFinIsNull(positionDTO.getVehicleId());

        if (activeTestDrive.isPresent()) {
            checkPositionConstraints(positionDTO, activeTestDrive.get());
        }
    }


    private Position savePosition(VehiclePositionDTO positionDTO) {
        Position position = new Position();
        position.setIdVehiculo(positionDTO.getVehicleId());
        position.setLatitud(positionDTO.getLatitude());
        position.setLongitud(positionDTO.getLongitude());
        position.setFechaHora(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
                config.getCoordenadasAgencia().getLat(),
                config.getCoordenadasAgencia().getLon()
        );
        return distance > config.getRadioAdmitidoKm();
    }

    private boolean isInDangerZone(VehiclePositionDTO position, DealershipConfigDTO config) {
        return config.getZonasRestringidas().stream().anyMatch(zone ->
                position.getLatitude() <= zone.getNoroeste().getLat() &&
                        position.getLatitude() >= zone.getSureste().getLat() &&
                        position.getLongitude() >= zone.getNoroeste().getLon() &&
                        position.getLongitude() <= zone.getSureste().getLon()
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
                testDrive.getEmployeeId(),
                testDrive.getId(),
                violationType + " at " + location
        );
    }

    private void restrictCustomer(TestDrive testDrive) {
        testDriveRepository.updateInteresadoRestringido(testDrive.getId(), true);
    }

    public VehiclePositionDTO getLastPosition(Long vehicleId) {
        Position position = positionRepository.findFirstByIdVehiculoOrderByFechaHoraDesc(vehicleId)
                .orElseThrow(() -> new RuntimeException("No positions found"));
        return convertToDTO(position);
    }


    public List<VehiclePositionDTO> getPositionTrack(Long vehicleId, LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return positionRepository.findByIdVehiculoAndFechaHoraBetweenOrderByFechaHoraAsc(
                        vehicleId,
                        start.format(formatter),
                        end.format(formatter)
                ).stream()
                .map(this::convertToDTO)
                .toList();
    }

    private VehiclePositionDTO convertToDTO(Position position) {
        VehiclePositionDTO dto = new VehiclePositionDTO();
        dto.setVehicleId(position.getIdVehiculo());
        dto.setLatitude(position.getLatitud());
        dto.setLongitude(position.getLongitud());
        dto.setTimestamp(position.getFechaHora());
        return dto;
    }
}