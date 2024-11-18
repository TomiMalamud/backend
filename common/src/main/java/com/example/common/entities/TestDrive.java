// common/src/main/java/com/example/common/entities/TestDrive.java
package com.example.common.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pruebas")
@Data
public class TestDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    private String comentarios;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehicle vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_interesado")
    private InterestedParty interesado;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Employee empleado;
}