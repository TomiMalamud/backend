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
public class TestDriveResponseDTO {
    private Long id;
    private Long vehicleId;
    private Long interestedId;
    private Long employeeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String comments;
}