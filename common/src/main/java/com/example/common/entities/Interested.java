// common/src/main/java/com/example/common/entities/Interested.java
package com.example.common.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "interesados")
@Data
public class Interested {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    private String documento;
    private String nombre;
    private String apellido;
    private Boolean restringido;

    @Column(name = "nro_licencia")
    private String nroLicencia;

    @Column(name = "fecha_vencimiento_licencia")
    private LocalDate fechaVencimientoLicencia;
}