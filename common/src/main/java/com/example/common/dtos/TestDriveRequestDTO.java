package com.example.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDriveRequestDTO {
    private Long vehicleId;
    private Long interestedId;
    private Long employeeId;
}