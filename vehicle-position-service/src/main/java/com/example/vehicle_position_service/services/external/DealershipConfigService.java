package com.example.vehicle_position_service.services.external;

import com.example.common.dtos.DealershipConfigDTO;
import com.example.common.dtos.DangerZoneDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

// Path: vehicle-position-service/src/main/java/com/example/vehicle_position_service/services/external/DealershipConfigService.java

@Service
@Slf4j
public class DealershipConfigService {
    private final RestTemplate restTemplate;
    private final String configServiceUrl;

    public DealershipConfigService(
            RestTemplate restTemplate,
            @Value("${external.config-service.url}") String configServiceUrl) {
        this.restTemplate = restTemplate;
        this.configServiceUrl = configServiceUrl;
    }

    public DealershipConfigDTO getConfig() {
        try {
            return restTemplate.getForObject(configServiceUrl, DealershipConfigDTO.class);
        } catch (Exception e) {
            log.error("Error fetching config: {}", e.getMessage());
            return getDefaultConfig();
        }
    }

    private DealershipConfigDTO getDefaultConfig() {
        DealershipConfigDTO config = new DealershipConfigDTO();
        DealershipConfigDTO.Coordinates agencyCoords = new DealershipConfigDTO.Coordinates();
        agencyCoords.setLat(42.50886738457441);
        agencyCoords.setLon(1.5347139324337429);
        config.setCoordenadasAgencia(agencyCoords);
        config.setRadioAdmitidoKm(5.0);
        config.setZonasRestringidas(new ArrayList<>());
        return config;
    }
}