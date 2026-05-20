package com.arthlnx.deliveryproj.delivery.tracking.domain.service;

import java.math.BigDecimal;

public interface CourierPayoutCalcService {
    BigDecimal calculateCourierPayout(Double distanceKm);

}
