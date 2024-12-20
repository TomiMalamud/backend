package com.example.common.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestDriveReportDTO {
    private Long testDriveId;
    private Long vehicleId;
    private Long customerId;
    private Long employeeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String comments;
}