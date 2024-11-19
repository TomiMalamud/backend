package com.example.test_drive_service.services;

import com.example.common.dtos.IncidentReportDTO;
import com.example.common.dtos.TestDriveReportDTO;
import com.example.common.entities.TestDrive;
import com.example.test_drive_service.repositories.TestDriveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final TestDriveRepository testDriveRepository;

    public List<IncidentReportDTO> getIncidents() {
        // Implementation pending based on how incidents are tracked
        // This could pull from a violations table or test drives with incidents
        return List.of(); // Placeholder
    }

    public List<IncidentReportDTO> getEmployeeIncidents(Long employeeId) {
        // Get incidents for specific employee
        return List.of(); // Placeholder
    }

    public double getVehicleMileage(Long vehicleId, LocalDateTime start, LocalDateTime end) {
        // Calculate mileage from positions within timeframe
        return 0.0; // Placeholder
    }

    public List<TestDriveReportDTO> getVehicleTestDriveDetails(Long vehicleId) {
        List<TestDrive> testDrives = testDriveRepository.findByVehicleId(vehicleId);

        return testDrives.stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }

    private TestDriveReportDTO convertToReportDTO(TestDrive testDrive) {
        return TestDriveReportDTO.builder()
                .testDriveId(testDrive.getId())
                .startTime(testDrive.getFechaHoraInicio())
                .endTime(testDrive.getFechaHoraFin())
                .comments(testDrive.getComentarios())
                // Other fields would be populated by joining with other entities
                .build();
    }
}