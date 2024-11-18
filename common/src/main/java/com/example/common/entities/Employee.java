// common/src/main/java/com/example/common/entities/Employee.java
package com.example.common.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "empleados")
@Data
public class Employee {
    @Id
    @Column(name = "legado")
    private Long legado;

    private String nombre;
    private String apellido;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;
}