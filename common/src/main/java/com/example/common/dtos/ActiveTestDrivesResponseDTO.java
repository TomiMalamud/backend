package com.example.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveTestDrivesResponseDTO {
    private Long testDriveId;
    private String vehiclePlate;
    private String customerName;
    private String employeeName;
    private LocalDateTime startTime;
    private Long durationInMinutes;
}