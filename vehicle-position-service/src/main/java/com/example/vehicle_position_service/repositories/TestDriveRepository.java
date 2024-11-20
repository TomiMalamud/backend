package com.example.vehicle_position_service.repositories;

import com.example.common.entities.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface TestDriveRepository extends JpaRepository<TestDrive, Long> {
    Optional<TestDrive> findByVehicleIdAndFechaHoraFinIsNull(Long vehicleId);

    @Modifying
    @Query("UPDATE TestDrive td SET td.interestedId = :restricted WHERE td.id = :id")
    void updateInteresadoRestringido(@Param("id") Long id, @Param("restricted") boolean restricted);
}