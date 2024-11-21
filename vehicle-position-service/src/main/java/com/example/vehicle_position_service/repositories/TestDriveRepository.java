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
    @Query("UPDATE Interested i SET i.restringido = :restricted WHERE i.id = (SELECT td.interestedId FROM TestDrive td WHERE td.id = :testDriveId)")
    void updateInteresadoRestringido(@Param("testDriveId") Long testDriveId, @Param("restricted") boolean restricted);
}