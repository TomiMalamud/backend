// DangerZoneDTO.java
package com.example.common.dtos;

import lombok.Data;

@Data
public class DangerZoneDTO {
    private Double nwLatitude;
    private Double nwLongitude;
    private Double seLatitude;
    private Double seLongitude;
    private String description;
}
