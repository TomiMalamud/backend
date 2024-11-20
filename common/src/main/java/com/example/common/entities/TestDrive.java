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

    @Column(name = "ID_VEHICULO")
    private Long vehicleId;

    @Column(name = "ID_INTERESADO")
    private Long interestedId;

    @Column(name = "ID_EMPLEADO")
    private Long employeeId;

    @Column(name = "FECHA_HORA_INICIO")
    private String fechaHoraInicio;

    @Column(name = "FECHA_HORA_FIN")
    private String fechaHoraFin;

    @Column(name = "COMENTARIOS")
    private String comentarios;

    // Add setter methods
    public void setVehiculo(Vehicle vehicle) {
        this.vehicleId = vehicle.getId();
    }

    public void setInteresado(Interested interested) {
        this.interestedId = interested.getId();
    }

    public void setEmpleado(Employee employee) {
        this.employeeId = employee.getLegajo();
    }
}