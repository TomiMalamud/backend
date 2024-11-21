package com.example.test_drive_service.services;

import com.example.common.dtos.IncidentReportDTO;
import com.example.common.dtos.TestDriveReportDTO;
import com.example.common.entities.*;
import com.example.test_drive_service.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final TestDriveRepository testDriveRepository;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;
    private final InterestedRepository interestedRepository;
    private final VehicleRepository vehicleRepository;

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

    public List<IncidentReportDTO> getIncidentReport() {
        return testDriveRepository.findAll().stream()
                .filter(this::isViolation)
                .map(this::convertToIncidentDTO)
                .collect(Collectors.toList());
    }

    public List<IncidentReportDTO> getEmployeeIncidents(Long employeeId) {
        return testDriveRepository.findByEmployeeId(employeeId).stream()
                .filter(this::isViolation)
                .map(this::convertToIncidentDTO)
                .collect(Collectors.toList());
    }

    private boolean isViolation(TestDrive testDrive) {
        return testDrive.getFechaHoraFin() != null &&
                testDrive.getComentarios() != null &&
                !testDrive.getComentarios().isEmpty();
    }

    private IncidentReportDTO convertToIncidentDTO(TestDrive testDrive) {
        // Fetch Employee Name
        String employeeName = null;
        if (testDrive.getEmployeeId() != null) {
            employeeName = employeeRepository.findById(testDrive.getEmployeeId())
                    .map(Employee::getNombre)
                    .orElse("Unknown Employee");
        }

        // Fetch Customer Name
        String customerName = null;
        if (testDrive.getInterestedId() != null) {
            customerName = interestedRepository.findById(testDrive.getInterestedId())
                    .map(Interested::getNombre)
                    .orElse("Unknown Customer");
        }

        // Fetch Vehicle Plate
        String vehiclePlate = null;
        if (testDrive.getVehicleId() != null) {
            vehiclePlate = vehicleRepository.findById(testDrive.getVehicleId())
                    .map(Vehicle::getPatente)
                    .orElse("Unknown Vehicle");
        }

        // Fetch Location (Assuming latest position)
        String location = null;
        List<Position> positions = positionRepository.findByIdVehiculo(testDrive.getVehicleId());
        if (!positions.isEmpty()) {
            Position latestPosition = positions.get(positions.size() - 1); // Or use a specific query to get the latest
            location = "Lat: " + latestPosition.getLatitud() + ", Lon: " + latestPosition.getLongitud();
        }

        return IncidentReportDTO.builder()
                .testDriveId(testDrive.getId())
                .violationType("RADIUS/DANGER_ZONE")
                .violationTime(testDrive.getFechaHoraFin() != null ? testDrive.getFechaHoraFin() : LocalDateTime.now())
                .employeeName(employeeName)
                .customerName(customerName)
                .vehiclePlate(vehiclePlate)
                .location(location)
                .build();
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
        final int R = 6371; // Earth's radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}