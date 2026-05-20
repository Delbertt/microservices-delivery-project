package com.arthlnx.deliveryproj.delivery.tracking.domain.service;

import com.arthlnx.deliveryproj.delivery.tracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {
    DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver);
}
