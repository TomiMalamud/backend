package com.example.test_drive_service.services;

import com.example.common.dtos.IncidentReportDTO;
import com.example.common.dtos.TestDriveReportDTO;
import com.example.common.entities.Position;
import com.example.common.entities.TestDrive;
import com.example.test_drive_service.repositories.TestDriveRepository;
import com.example.test_drive_service.repositories.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final TestDriveRepository testDriveRepository;
    private final PositionRepository positionRepository;

    public List<TestDriveReportDTO> getAllTestDrives() {
        return testDriveRepository.findAll().stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }

    public List<TestDriveReportDTO> getEmployeeTestDrives(Long employeeId) {
        return testDriveRepository.findByEmployeeId(employeeId).stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }

    public double getVehicleMileage(Long vehicleId, LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Position> positions = positionRepository.findByIdVehiculoAndFechaHoraBetween(
                vehicleId, start.format(formatter), end.format(formatter));

        return calculateTotalDistance(positions);
    }

    public List<TestDriveReportDTO> getVehicleTestDriveDetails(Long vehicleId) {
        return testDriveRepository.findByVehicleId(vehicleId).stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }

    private TestDriveReportDTO convertToReportDTO(TestDrive testDrive) {
        return TestDriveReportDTO.builder()
                .testDriveId(testDrive.getId())
                .vehicleId(testDrive.getVehicleId())
                .customerId(testDrive.getInterestedId())
                .employeeId(testDrive.getEmployeeId())
                .startTime(testDrive.getFechaHoraInicio())
                .endTime(testDrive.getFechaHoraFin())
                .comments(testDrive.getComentarios())
                .build();
    }

    private double calculateTotalDistance(List<Position> positions) {
        double totalDistance = 0.0;
        for (int i = 1; i < positions.size(); i++) {
            Position prev = positions.get(i - 1);
            Position curr = positions.get(i);
            totalDistance += calculateDistance(
                    prev.getLatitud(), prev.getLongitud(),
                    curr.getLatitud(), curr.getLongitud()
            );
        }
        return totalDistance;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }
}