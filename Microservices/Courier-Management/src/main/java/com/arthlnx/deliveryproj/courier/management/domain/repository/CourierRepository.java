package com.arthlnx.deliveryproj.courier.management.domain.repository;

import com.arthlnx.deliveryproj.courier.management.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
}
