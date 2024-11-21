package com.example.test_drive_service.repositories;

import com.example.common.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    // Add custom query methods if needed
}
