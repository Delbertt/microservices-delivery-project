package com.arthlnx.deliveryproj.courier.management.domain.service;

import com.arthlnx.deliveryproj.courier.management.api.modelDto.CourierDto;
import com.arthlnx.deliveryproj.courier.management.domain.model.Courier;
import com.arthlnx.deliveryproj.courier.management.domain.repository.CourierRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierRegistrationService {
    private final CourierRepository courierRepository;

    public Courier create(@Valid CourierDto courierDto) {
        Courier courier = Courier.brandNew(
                courierDto.getName(),
                courierDto.getPhone());
        return courierRepository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, @Valid CourierDto courierDto) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "courierId " + courierId + " not found")
        );
        courier.setName(courierDto.getName());
        courier.setPhone(courierDto.getPhone());
        return courierRepository.saveAndFlush(courier);
    }
}
