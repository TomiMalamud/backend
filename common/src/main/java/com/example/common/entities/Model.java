// common/src/main/java/com/example/common/entities/Model.java
package com.example.common.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "modelos")
@Data
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Brand marca;
}