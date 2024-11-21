// Path: common/src/main/java/com/example/common/dtos/DealershipConfigDTO.java

package com.example.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DealershipConfigDTO {
    @JsonProperty("coordenadasAgencia")
    private Coordinates coordenadasAgencia;

    @JsonProperty("radioAdmitidoKm")
    private Double radioAdmitidoKm;

    @JsonProperty("zonasRestringidas")
    private List<RestrictedZone> zonasRestringidas;

    @Data
    public static class Coordinates {
        private Double lat;
        private Double lon;
    }

    @Data
    public static class RestrictedZone {
        @JsonProperty("noroeste")
        private Coordinates noroeste;

        @JsonProperty("sureste")
        private Coordinates sureste;
    }
}