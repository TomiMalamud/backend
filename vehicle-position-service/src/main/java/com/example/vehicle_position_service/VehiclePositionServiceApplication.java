package com.example.vehicle_position_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@EntityScan("com.example.common.entities")
@EnableJpaRepositories("com.example.vehicle_position_service.repositories")
public class VehiclePositionServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(VehiclePositionServiceApplication.class, args);
	}
}
