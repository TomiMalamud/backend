package com.example.test_drive_service.application.controllers;

import com.example.common.dtos.TestDriveRequestDTO;
import com.example.common.dtos.TestDriveResponseDTO;
import com.example.common.dtos.TestDriveEndRequestDTO;
import com.example.test_drive_service.services.TestDriveService;
import com.example.test_drive_service.application.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-drives")
@RequiredArgsConstructor
public class TestDriveController {
    private final TestDriveService testDriveService;

    @PostMapping
    public ResponseEntity<?> createTestDrive(@RequestBody TestDriveRequestDTO request) {
        try {
            TestDriveResponseDTO response = testDriveService.createTestDrive(request);
            return ResponseHandler.success(response);
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveTestDrives() {
        try {
            List<TestDriveResponseDTO> activeTestDrives = testDriveService.getActiveTestDrives();
            return ResponseHandler.success(activeTestDrives);
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<?> endTestDrive(
            @PathVariable Long id,
            @RequestBody TestDriveEndRequestDTO request) {
        try {
            request.setTestDriveId(id);
            TestDriveResponseDTO response = testDriveService.endTestDrive(request);
            return ResponseHandler.success(response);
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }
}
