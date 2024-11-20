package com.example.common.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class TestDriveReportDTO {
    private Long testDriveId;
    private Long vehicleId;
    private Long customerId;
    private Long employeeId;
    private String startTime;
    private String endTime;
    private String comments;
}