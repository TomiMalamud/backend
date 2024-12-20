package com.example.test_drive_service.application.controllers;

import com.example.test_drive_service.application.ResponseHandler;
import com.example.test_drive_service.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    // Add these two new endpoints
    @GetMapping("/incidents")
    public ResponseEntity<?> getIncidentReport() {
        return handleRequest(() -> ResponseHandler.success(reportService.getIncidentReport()));
    }

    @GetMapping("/incidents/employee/{employeeId}")
    public ResponseEntity<?> getEmployeeIncidents(@PathVariable Long employeeId) {
        return handleRequest(() -> ResponseHandler.success(reportService.getEmployeeIncidents(employeeId)));
    }

    // Keep your existing endpoints
    @GetMapping("/test-drives")
    public ResponseEntity<?> getAllTestDrives() {
        return handleRequest(() -> ResponseHandler.success(reportService.getAllTestDrives()));
    }

    @GetMapping("/test-drives/employee/{employeeId}")
    public ResponseEntity<?> getEmployeeTestDrives(@PathVariable Long employeeId) {
        return handleRequest(() -> ResponseHandler.success(reportService.getEmployeeTestDrives(employeeId)));
    }

    @GetMapping("/vehicle/{vehicleId}/mileage")
    public ResponseEntity<?> getVehicleMileage(
            @PathVariable Long vehicleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return handleRequest(() -> ResponseHandler.success(reportService.getVehicleMileage(vehicleId, start, end)));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<?> getVehicleTestDriveDetails(@PathVariable Long vehicleId) {
        return handleRequest(() -> ResponseHandler.success(reportService.getVehicleTestDriveDetails(vehicleId)));
    }

    // Keep your utility methods
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
