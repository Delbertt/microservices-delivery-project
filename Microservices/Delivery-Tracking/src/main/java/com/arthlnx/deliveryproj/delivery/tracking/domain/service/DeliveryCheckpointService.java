package com.arthlnx.deliveryproj.delivery.tracking.domain.service;

import com.arthlnx.deliveryproj.delivery.tracking.domain.exception.DomainException;
import com.arthlnx.deliveryproj.delivery.tracking.domain.model.Delivery;
import com.arthlnx.deliveryproj.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {
    private final DeliveryRepository deliveryRepository;

    public void place(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException(
                        "Delivery with id " + deliveryId + " not found")
        );

        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
    }

    public void pickUp(UUID deliveryId, UUID courierId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException(
                        "Delivery with id " + deliveryId + " not found")
                );

        delivery.pickup(courierId);
        deliveryRepository.saveAndFlush(delivery);
    }

    public void markDelivered(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException(
                        "Delivery with id " + deliveryId + " not found")
                );

        delivery.markDelivered();
        deliveryRepository.saveAndFlush(delivery);
    }
}
