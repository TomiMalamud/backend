package com.example.test_drive_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.common.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    // Add custom query methods if needed
}
