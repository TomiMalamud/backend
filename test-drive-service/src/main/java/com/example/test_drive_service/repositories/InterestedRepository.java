package com.example.test_drive_service.repositories;

import com.example.common.entities.Interested;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InterestedRepository extends JpaRepository<Interested, Long> {
    List<Interested> findByRestringido(boolean restricted);
    Optional<Interested> findByDocumentoAndTipoDocumento(String documento, String tipoDocumento);
    List<Interested> findByFechaVencimientoLicenciaBefore(LocalDateTime date);
}