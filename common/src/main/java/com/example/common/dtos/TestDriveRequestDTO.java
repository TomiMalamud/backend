package com.example.common.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDriveRequestDTO {
    private Long vehicleId;
    private Long interestedId;
    private Long employeeId;
}