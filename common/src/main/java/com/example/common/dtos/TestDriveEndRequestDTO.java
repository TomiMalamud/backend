package com.example.common.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDriveEndRequestDTO {
    private Long testDriveId;
    private String comments;
}