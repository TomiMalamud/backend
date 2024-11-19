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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final TestDriveRepository testDriveRepository;
    private final PositionRepository positionRepository;

    public List<IncidentReportDTO> getIncidents() {
        List<TestDrive> testDrives = testDriveRepository.findByHasViolationsTrue();
        return testDrives.stream()
                .map(this::convertToIncidentDTO)
                .collect(Collectors.toList());
    }

    public List<IncidentReportDTO> getEmployeeIncidents(Long employeeId) {
        List<TestDrive> testDrives = testDriveRepository.findByEmpleado_LegajoAndHasViolationsTrue(employeeId);
        return testDrives.stream()
                .map(this::convertToIncidentDTO)
                .collect(Collectors.toList());
    }

    public double getVehicleMileage(Long vehicleId, LocalDateTime start, LocalDateTime end) {
        List<Position> positions = positionRepository.findByVehiculo_IdAndFechaHoraBetween(
                vehicleId, start, end);

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

    public List<TestDriveReportDTO> getVehicleTestDriveDetails(Long vehicleId) {
        List<TestDrive> testDrives = testDriveRepository.findByVehiculo_Id(vehicleId);
        return testDrives.stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }

    private TestDriveReportDTO convertToReportDTO(TestDrive testDrive) {
        return TestDriveReportDTO.builder()
                .testDriveId(testDrive.getId())
                .vehiclePlate(testDrive.getVehiculo().getPatente())
                .customerName(testDrive.getInteresado().getNombre() + " " + testDrive.getInteresado().getApellido())
                .employeeName(testDrive.getEmpleado().getNombre() + " " + testDrive.getEmpleado().getApellido())
                .startTime(testDrive.getFechaHoraInicio())
                .endTime(testDrive.getFechaHoraFin())
                .comments(testDrive.getComentarios())
                .build();
    }

    private IncidentReportDTO convertToIncidentDTO(TestDrive testDrive) {
        return IncidentReportDTO.builder()
                .testDriveId(testDrive.getId())
                .employeeName(testDrive.getEmpleado().getNombre() + " " + testDrive.getEmpleado().getApellido())
                .customerName(testDrive.getInteresado().getNombre() + " " + testDrive.getInteresado().getApellido())
                .vehiclePlate(testDrive.getVehiculo().getPatente())
                .build();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }
}