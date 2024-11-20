package com.example.common.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VehiclePositionDTO {
    private Long vehicleId;
    private Double latitude;
    private Double longitude;
    private String timestamp;  // Change type from LocalDateTime to String
}