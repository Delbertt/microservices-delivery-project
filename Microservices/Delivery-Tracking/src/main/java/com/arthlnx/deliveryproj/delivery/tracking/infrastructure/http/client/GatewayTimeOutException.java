package com.arthlnx.deliveryproj.delivery.tracking.infrastructure.http.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimeOutException extends RuntimeException {

    public GatewayTimeOutException () {}

    public GatewayTimeOutException (Throwable cause) {
        super(cause);
    }
}
