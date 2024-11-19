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
public class NotificationResponseDTO {
    private Long id;
    private String phoneNumber;
    private String message;
    private String type;
    private LocalDateTime timestamp;
    private boolean sent;
}
