// common/src/main/java/com/example/common/entities/Position.java
package com.example.common.entities;

import jakarta.persistence.*;
import lombok.Data;

// Position.java
@Entity
@Table(name = "Posiciones")
@Data
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ID_VEHICULO")
    private Long idVehiculo;

    @Column(name = "FECHA_HORA")
    private String fechaHora;

    @Column(name = "LATITUD")
    private Double latitud;

    @Column(name = "LONGITUD")
    private Double longitud;
}
