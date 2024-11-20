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
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO")  // Column 2: VALUE 1
    private Vehicle vehiculo;

    @ManyToOne
    @JoinColumn(name = "ID_INTERESADO")  // Column 3: VALUE 1
    private Interested interesado;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")   // Column 4: VALUE 2
    private Employee empleado;

    @Column(name = "FECHA_HORA_INICIO", columnDefinition = "TEXT")  // Column 5: 2024-11-19 23:37:10
    private LocalDateTime fechaHoraInicio;

    @Column(name = "FECHA_HORA_FIN", columnDefinition = "TEXT")  // Column 6: 2024-11-20 00:37:10
    private LocalDateTime fechaHoraFin;

    @Column(name = "COMENTARIOS")  // Column 7: "Comentario"
    private String comentarios;
}