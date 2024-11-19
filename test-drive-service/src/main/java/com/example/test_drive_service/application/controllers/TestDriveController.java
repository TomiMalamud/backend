package com.example.test_drive_service.application.controllers;

import com.example.common.dtos.TestDriveRequestDTO;
import com.example.common.dtos.TestDriveResponseDTO;
import com.example.common.dtos.TestDriveEndRequestDTO;
import com.example.test_drive_service.services.TestDriveService;
import com.example.test_drive_service.application.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/test-drives")
@RequiredArgsConstructor
public class TestDriveController {
    private final TestDriveService testDriveService;

    @PostMapping
    public ResponseEntity<?> createTestDrive(@Valid @RequestBody TestDriveRequestDTO request) {
        return handleRequest(() -> {
            TestDriveResponseDTO response = testDriveService.createTestDrive(request);
            return ResponseHandler.success(response);
        });
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveTestDrives() {
        return handleRequest(() -> {
            List<TestDriveResponseDTO> activeTestDrives = testDriveService.getActiveTestDrives();
            return ResponseHandler.success(activeTestDrives);
        });
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<?> endTestDrive(@PathVariable Long id, @Valid @RequestBody TestDriveEndRequestDTO request) {
        return handleRequest(() -> {
            request.setTestDriveId(id);
            TestDriveResponseDTO response = testDriveService.endTestDrive(request);
            return ResponseHandler.success(response);
        });
    }

    // Utility method to centralize exception handling
    private ResponseEntity<?> handleRequest(RequestHandler handler) {
        try {
            return handler.handle();
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @FunctionalInterface
    private interface RequestHandler {
        ResponseEntity<?> handle() throws Exception;
    }
}

