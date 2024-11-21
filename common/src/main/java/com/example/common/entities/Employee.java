// common/src/main/java/com/example/common/entities/Employee.java
package com.example.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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