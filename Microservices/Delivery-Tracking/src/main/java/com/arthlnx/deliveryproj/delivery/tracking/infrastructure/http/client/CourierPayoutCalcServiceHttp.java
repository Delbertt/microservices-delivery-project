package com.arthlnx.deliveryproj.delivery.tracking.infrastructure.http.client;

import com.arthlnx.deliveryproj.delivery.tracking.domain.service.CourierPayoutCalcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalcServiceHttp implements CourierPayoutCalcService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculateCourierPayout(Double distanceKm) {
        var courierPayoutResultModel = courierAPIClient.payoutCalculation(
                new CourierPayoutCalculationDto(distanceKm));
        return courierPayoutResultModel.getPayoutFee();
    }

}
