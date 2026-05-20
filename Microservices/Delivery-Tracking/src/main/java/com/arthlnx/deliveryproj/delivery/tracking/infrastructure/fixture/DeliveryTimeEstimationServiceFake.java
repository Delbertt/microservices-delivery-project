package com.arthlnx.deliveryproj.delivery.tracking.infrastructure.fixture;

import com.arthlnx.deliveryproj.delivery.tracking.domain.model.ContactPoint;
import com.arthlnx.deliveryproj.delivery.tracking.domain.service.DeliveryEstimate;
import com.arthlnx.deliveryproj.delivery.tracking.domain.service.DeliveryTimeEstimationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DeliveryTimeEstimationServiceFake implements DeliveryTimeEstimationService {
    @Override
    public DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver) {
        return new DeliveryEstimate(
                Duration.ofHours(1),
                4.0
        );
    }
}
