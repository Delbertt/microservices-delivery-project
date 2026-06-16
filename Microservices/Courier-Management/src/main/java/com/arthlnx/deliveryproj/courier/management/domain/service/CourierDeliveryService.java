package com.arthlnx.deliveryproj.courier.management.domain.service;

import com.arthlnx.deliveryproj.courier.management.domain.model.Courier;
import com.arthlnx.deliveryproj.courier.management.domain.repository.CourierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourierDeliveryService {

    private final CourierRepository courierRepository;

    public void assign(UUID deliveryId) {
        Courier courier = courierRepository.findTop1ByOrderByLastFulfilledDeliveryAtAsc()
                .orElseThrow();

        courier.assign(deliveryId);
        courierRepository.save(courier);

        log.info("Courier {} assigned to delivery {}", courier.getId(), deliveryId);
    }

    public void fulfill(UUID deliveryId) {
        Courier courier = courierRepository.findByPendingDeliveriesId(deliveryId)
                .orElseThrow();

        courier.fulfilled(deliveryId);
        courierRepository.save(courier);

        log.info("Delivery {} fulfilled", deliveryId);
    }
}
