// TestDriveService.java
package com.example.test_drive_service.services;

import com.example.common.dtos.TestDriveRequestDTO;
import com.example.common.dtos.TestDriveResponseDTO;
import com.example.common.dtos.TestDriveEndRequestDTO;
import com.example.common.entities.*;
import com.example.test_drive_service.exceptions.BusinessException;
import com.example.test_drive_service.exceptions.ResourceNotFoundException;
import com.example.test_drive_service.repositories.TestDriveRepository;
import com.example.test_drive_service.repositories.InterestedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TestDriveService {
    private final TestDriveRepository testDriveRepository;
    private final InterestedRepository interestedRepository;
    private final NotificationService notificationService;

    public TestDriveResponseDTO createTestDrive(TestDriveRequestDTO request) {
        validateTestDriveRequest(request);

        TestDrive testDrive = new TestDrive();
        testDrive.setVehicleId(request.getVehicleId());
        testDrive.setInterestedId(request.getInterestedId());
        testDrive.setEmployeeId(request.getEmployeeId());

        testDrive.setFechaHoraInicio(LocalDateTime.now());
        testDrive.setFechaHoraFin(null);

        return convertToDTO(testDriveRepository.save(testDrive));
    }

    public TestDriveResponseDTO endTestDrive(TestDriveEndRequestDTO request) {
        TestDrive testDrive = testDriveRepository.findById(request.getTestDriveId())
                .orElseThrow(() -> new ResourceNotFoundException("Test drive not found"));

        if (testDrive.getFechaHoraFin() != null) {
            throw new BusinessException("Test drive is already ended");
        }

        testDrive.setFechaHoraFin(LocalDateTime.now());
        testDrive.setComentarios(request.getComments());

        return convertToDTO(testDriveRepository.save(testDrive));
    }

    private void validateTestDriveRequest(TestDriveRequestDTO request) {
        Interested interested = interestedRepository.findById(request.getInterestedId())
                .orElseThrow(() -> new ResourceNotFoundException("Interested party not found"));

        if (interested.getRestringido()) {
            throw new BusinessException("Customer is restricted from test drives");
        }

        if (interested.getFechaVencimientoLicencia().isBefore(LocalDate.now())) {
            throw new BusinessException("Driver's license is expired");
        }

        if (isVehicleInUse(request.getVehicleId())) {
            throw new BusinessException("Vehicle is currently in use");
        }
    }

    private boolean isVehicleInUse(Long vehicleId) {
        return !testDriveRepository.findByVehicleIdAndFechaHoraFinIsNull(vehicleId).isEmpty();
    }

    @Transactional(readOnly = true)
    public List<TestDriveResponseDTO> getActiveTestDrives() {
        return testDriveRepository.findByFechaHoraFinIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TestDriveResponseDTO convertToDTO(TestDrive testDrive) {
        return TestDriveResponseDTO.builder()
                .id(testDrive.getId())
                .vehicleId(testDrive.getVehicleId())
                .interestedId(testDrive.getInterestedId())
                .employeeId(testDrive.getEmployeeId())
                .startTime(testDrive.getFechaHoraInicio())
                .endTime(testDrive.getFechaHoraFin())
                .comments(testDrive.getComentarios())
                .build();
    }
}
