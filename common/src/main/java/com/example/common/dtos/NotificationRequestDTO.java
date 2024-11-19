package com.example.common.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {
    private List<String> phoneNumbers;
    private String message;
    private String type;
}