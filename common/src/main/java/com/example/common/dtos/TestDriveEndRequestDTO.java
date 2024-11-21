package com.example.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDriveEndRequestDTO {
    private Long testDriveId;
    private String comments;
}