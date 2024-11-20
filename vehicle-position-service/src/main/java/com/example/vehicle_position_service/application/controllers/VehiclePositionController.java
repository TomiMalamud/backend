package com.example.vehicle_position_service.application.controllers;

import com.example.common.dtos.VehiclePositionDTO;
import com.example.vehicle_position_service.services.VehiclePositionService;
import com.example.vehicle_position_service.application.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
// Path: vehicle-position-service/src/main/java/com/example/vehicle_position_service/application/controllers/VehiclePositionController.java

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class VehiclePositionController {
    private final VehiclePositionService positionService;

    @PostMapping
    public ResponseEntity<?> receivePosition(@RequestBody VehiclePositionDTO position) {
        try {
            positionService.processPosition(position);
            return ResponseHandler.success("Position received successfully");
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @GetMapping("/{vehicleId}/last")
    public ResponseEntity<?> getLastPosition(@PathVariable Long vehicleId) {
        try {
            VehiclePositionDTO position = positionService.getLastPosition(vehicleId);
            return ResponseHandler.success(position);
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }

    @GetMapping("/{vehicleId}/track")
    public ResponseEntity<?> getPositionTrack(
            @PathVariable Long vehicleId,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime start = LocalDateTime.parse(startTime, formatter);
            LocalDateTime end = LocalDateTime.parse(endTime, formatter);

            List<VehiclePositionDTO> positions = positionService.getPositionTrack(vehicleId, start, end);
            return ResponseHandler.success(positions);
        } catch (Exception e) {
            return ResponseHandler.error(e);
        }
    }
}