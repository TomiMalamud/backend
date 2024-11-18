// common/src/main/java/com/example/common/entities/Vehicle.java
package com.example.common.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vehiculos")
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patente;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Model modelo;
}