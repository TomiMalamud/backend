package com.example.common.dtos;

import lombok.Data;

@Data
public class VehiclePositionDTO {
    private Long vehicleId;
    private Double latitude;
    private Double longitude;
    private String timestamp;  // Change type from LocalDateTime to String
}