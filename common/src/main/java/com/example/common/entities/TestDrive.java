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
    @GeneratedValue(generator = "test_drive_sequence")
    @TableGenerator(
            name = "test_drive_sequence",
            table = "sqlite_sequence",
            pkColumnName = "name",
            valueColumnName = "seq",
            pkColumnValue = "PRUEBAS",
            allocationSize = 1
    )
    @Column(name = "ID")
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