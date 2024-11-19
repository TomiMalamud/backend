package com.example.vehicle_position_service.application.controllers;

import com.example.common.dtos.VehiclePositionDTO;
import com.example.vehicle_position_service.services.VehiclePositionService;
import com.example.vehicle_position_service.application.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}