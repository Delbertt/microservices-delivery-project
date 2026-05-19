package com.arthlnx.deliveryproj.delivery.tracking.api.controller;

import com.arthlnx.deliveryproj.delivery.tracking.api.modelDto.CourierDto;
import com.arthlnx.deliveryproj.delivery.tracking.api.modelDto.DeliveryDto;
import com.arthlnx.deliveryproj.delivery.tracking.domain.model.Delivery;
import com.arthlnx.deliveryproj.delivery.tracking.domain.repository.DeliveryRepository;
import com.arthlnx.deliveryproj.delivery.tracking.domain.service.DeliveryCheckpointService;
import com.arthlnx.deliveryproj.delivery.tracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryPreparationService deliveryPreparationService;
    private final DeliveryCheckpointService deliveryCheckpointService;

    private final DeliveryRepository deliveryRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryDto deliveryDto){
        return deliveryPreparationService.draft(deliveryDto);
    }

    @PutMapping("/{deliveryId}")
    public Delivery edit(@PathVariable UUID deliveryId,
            @RequestBody @Valid DeliveryDto deliveryDto){
        return deliveryPreparationService.edit(deliveryId, deliveryDto);
    }

    @GetMapping
    public PagedModel<Delivery> findAll(@PageableDefault Pageable pageable){
        return new PagedModel<>(
                deliveryRepository.findAll(pageable)
        );
    }

    @GetMapping("/{deliveryId}")
    public Delivery findById(@PathVariable UUID deliveryId){
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{deliveryId}")
    public void place (@PathVariable UUID deliveryId) {
        deliveryCheckpointService.place(deliveryId);
    }

    @PostMapping("/{deliveryId}/pickups")
    public void pickup (@PathVariable UUID deliveryId,
                        @Valid @RequestBody CourierDto courierDto) {
        deliveryCheckpointService.pickUp(deliveryId, courierDto.getCourierId());
    }

    @PostMapping("/{deliveryId}/completion")
    public void complete (@PathVariable UUID deliveryId) {
        deliveryCheckpointService.markDelivered(deliveryId);
    }
}
