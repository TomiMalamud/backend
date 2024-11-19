package com.example.vehicle_position_service.services.external;

import com.example.common.dtos.DealershipConfigDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
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
        return restTemplate.getForObject(configServiceUrl, DealershipConfigDTO.class);
    }
}