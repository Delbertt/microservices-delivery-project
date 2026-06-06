package com.arthlnx.deliveryproj.courier.management.api.controller;

import com.arthlnx.deliveryproj.courier.management.api.modelDto.CourierDto;
import com.arthlnx.deliveryproj.courier.management.api.modelDto.CourierPayoutCalculationDto;
import com.arthlnx.deliveryproj.courier.management.api.modelDto.CourierPayoutResultModel;
import com.arthlnx.deliveryproj.courier.management.domain.model.Courier;
import com.arthlnx.deliveryproj.courier.management.domain.repository.CourierRepository;
import com.arthlnx.deliveryproj.courier.management.domain.service.CourierPayoutService;
import com.arthlnx.deliveryproj.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
public class CourierController {
    private final CourierRepository courierRepository;
    private final CourierRegistrationService courierRegistrationService;

    private final CourierPayoutService courierPayoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier create(@Valid @RequestBody CourierDto courierDto) {
        return courierRegistrationService.create(courierDto);
    }

    @PutMapping("/{courierId}")
    public Courier update(@PathVariable UUID courierId,
                          @Valid @RequestBody CourierDto courierDto) {
        return courierRegistrationService.update(courierId, courierDto);
    }

    @GetMapping
    public PagedModel<Courier> findAll(@PageableDefault Pageable pageable) {
        return new PagedModel<>(
                courierRepository.findAll(pageable));
    }

    @GetMapping("/{courierId}")
    public Courier findById(@PathVariable UUID courierId) {
        return courierRepository.findById(courierId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Courier with id " + courierId + " not found")
        );
    }

    @PostMapping("/payout-calculation")
    public CourierPayoutResultModel calculate (
            @RequestBody CourierPayoutCalculationDto courierPayoutCalculationDto) {
        BigDecimal payoutFee = courierPayoutService
                .calculate(courierPayoutCalculationDto.getDistanceKm());
        return new CourierPayoutResultModel(payoutFee);
    }
}
