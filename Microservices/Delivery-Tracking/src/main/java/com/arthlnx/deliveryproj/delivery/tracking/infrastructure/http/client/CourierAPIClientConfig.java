package com.arthlnx.deliveryproj.delivery.tracking.infrastructure.http.client;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class CourierAPIClientConfig {

    // The returned CourierAPIClient will be available for dependency injection elsewhere
    @Bean
    public CourierAPIClient courierAPIClient(RestClient.Builder builder) {
        RestClient restclient = builder.baseUrl("http://localhost:8081/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restclient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory
                .builderFor(adapter).build();

        return proxyFactory.createClient(CourierAPIClient.class);
    }
}
