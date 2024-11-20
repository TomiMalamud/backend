package com.example.test_drive_service.services;
import java.time.format.DateTimeFormatter;  // Add this import
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

        Vehicle vehicle = new Vehicle();
        vehicle.setId(request.getVehicleId());
        testDrive.setVehiculo(vehicle);

        Interested interested = new Interested();
        interested.setId(request.getInterestedId());
        testDrive.setInteresado(interested);

        Employee employee = new Employee();
        employee.setLegajo(request.getEmployeeId());
        testDrive.setEmpleado(employee);

        // Format current date as string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        testDrive.setFechaHoraInicio(LocalDateTime.now());

        return convertToDTO(testDriveRepository.save(testDrive));
    }

    private void validateTestDriveRequest(TestDriveRequestDTO request) {
        Interested interested = interestedRepository.findById(request.getInterestedId())
                .orElseThrow(() -> new ResourceNotFoundException("Interested party not found"));

        if (interested.getRestringido()) {
            throw new BusinessException("Customer is restricted from test drives");
        }

        // Convert LocalDateTime to LocalDate for comparison
        if (interested.getFechaVencimientoLicencia().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new BusinessException("Driver's license is expired");
        }

        if (isVehicleInUse(request.getVehicleId())) {
            throw new BusinessException("Vehicle is currently in use");
        }
    }

    private boolean isVehicleInUse(Long vehicleId) {
        return !testDriveRepository.findByVehiculo_IdAndFechaHoraFinIsNull(vehicleId).isEmpty();
    }

    @Transactional(readOnly = true)
    public List<TestDriveResponseDTO> getActiveTestDrives() {
        return testDriveRepository.findByFechaHoraFinIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

    private TestDriveResponseDTO convertToDTO(TestDrive testDrive) {
        return TestDriveResponseDTO.builder()
                .id(testDrive.getId())
                .vehicleId(testDrive.getVehiculo().getId())
                .interestedId(testDrive.getInteresado().getId())
                .employeeId(testDrive.getEmpleado().getLegajo())
                .startTime(testDrive.getFechaHoraInicio())
                .endTime(testDrive.getFechaHoraFin())
                .comments(testDrive.getComentarios())
                .build();
    }
}