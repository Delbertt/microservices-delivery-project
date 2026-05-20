package com.arthlnx.deliveryproj.courier.management.domain.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CourierPayoutService {
    public BigDecimal calculate(Double distanceKm) {
        return new BigDecimal("10")
                .multiply(new BigDecimal(distanceKm))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
