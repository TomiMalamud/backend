package com.example.test_drive_service.application.controllers;

import com.example.test_drive_service.services.ReportService;
import com.example.test_drive_service.application.ResponseHandler;
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

    @GetMapping("/incidents")
    public ResponseEntity<?> getAllIncidents() {
        try {
            return ResponseHandler.success(reportService.getIncidents());
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @GetMapping("/incidents/employee/{employeeId}")
    public ResponseEntity<?> getEmployeeIncidents(@PathVariable Long employeeId) {
        try {
            return ResponseHandler.success(reportService.getEmployeeIncidents(employeeId));
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @GetMapping("/vehicle/{vehicleId}/mileage")
    public ResponseEntity<?> getVehicleMileage(
            @PathVariable Long vehicleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            return ResponseHandler.success(
                    reportService.getVehicleMileage(vehicleId, start, end));
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<?> getVehicleTestDriveDetails(@PathVariable Long vehicleId) {
        try {
            return ResponseHandler.success(
                    reportService.getVehicleTestDriveDetails(vehicleId));
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }
}