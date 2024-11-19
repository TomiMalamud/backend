package com.example.common.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
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