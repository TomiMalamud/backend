// DealershipConfigDTO.java
package com.example.common.dtos;

import lombok.Data;
import java.util.List;

@Data
public class DealershipConfigDTO {
    private Double dealershipLatitude;
    private Double dealershipLongitude;
    private Double maxRadius;
    private List<DangerZoneDTO> dangerZones;
}
