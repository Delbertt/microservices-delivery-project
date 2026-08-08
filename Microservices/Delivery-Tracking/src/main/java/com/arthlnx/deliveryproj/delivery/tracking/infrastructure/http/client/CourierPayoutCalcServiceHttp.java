package com.arthlnx.deliveryproj.delivery.tracking.infrastructure.http.client;

import com.arthlnx.deliveryproj.delivery.tracking.domain.service.CourierPayoutCalcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalcServiceHttp implements CourierPayoutCalcService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculateCourierPayout(Double distanceKm) {
        try {
            var courierPayoutResultModel = courierAPIClient.payoutCalculation(
                    new CourierPayoutCalculationDto(distanceKm));
            return courierPayoutResultModel.getPayoutFee();

        } catch(ResourceAccessException e) {
            throw new GatewayTimeOutException(e);
        } catch(HttpServerErrorException | IllegalArgumentException e) {
            throw new BadGatewayException(e);
        }
    }
}
