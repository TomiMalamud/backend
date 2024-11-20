// common/src/main/java/com/example/common/entities/Employee.java
package com.example.common.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Empleados")
@Data
public class Employee {
    @Id
    @Column(name = "legajo")
    private Long legajo;

    private String nombre;
    private String apellido;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;
}