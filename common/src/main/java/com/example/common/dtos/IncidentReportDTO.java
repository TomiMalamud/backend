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
public class IncidentReportDTO {
    private Long testDriveId;
    private String violationType;
    private LocalDateTime violationTime;
    private String employeeName;
    private String customerName;
    private String vehiclePlate;
    private String location;
}