// common/src/main/java/com/example/common/entities/TestDrive.java
package com.example.common.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Pruebas")
@Data
public class TestDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_VEHICULO")
    private Long vehicleId;

    @Column(name = "ID_INTERESADO")
    private Long interestedId;

    @Column(name = "ID_EMPLEADO")
    private Long employeeId;

    @Column(name = "FECHA_HORA_INICIO")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "FECHA_HORA_FIN")
    private LocalDateTime fechaHoraFin;

    @Column(name = "COMENTARIOS")
    private String comentarios;
}