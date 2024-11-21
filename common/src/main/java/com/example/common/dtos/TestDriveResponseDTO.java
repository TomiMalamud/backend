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
public class TestDriveResponseDTO {
    private Long id;
    private Long vehicleId;
    private Long interestedId;
    private Long employeeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String comments;
}