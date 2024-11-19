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
public class TestDriveReportDTO {
    private Long testDriveId;
    private String vehiclePlate;
    private String customerName;
    private String employeeName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalDistance;
    private String comments;
    private boolean hadViolations;
}