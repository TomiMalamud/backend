package com.example.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViolationNotificationDTO {
    private Long testDriveId;
    private Long employeeId;
    private String violationType;  // "RADIUS" or "DANGER_ZONE"
    private String location;
}