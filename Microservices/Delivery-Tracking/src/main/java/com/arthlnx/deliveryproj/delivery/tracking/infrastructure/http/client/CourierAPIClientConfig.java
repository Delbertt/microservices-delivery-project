package com.arthlnx.deliveryproj.delivery.tracking.infrastructure.http.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CourierAPIClientConfig {

    @Bean
    @Primary // Primary bean noted for enabling eureka client safely make a
    // request to it server without going trought the load balancer
    public RestClient.Builder plainRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean(name = "courierRestClientBuilder")
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public CourierAPIClient courierAPIClient(
            @Qualifier("courierRestClientBuilder") RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder
                .baseUrl("http://courier-management/")
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();

        return proxyFactory.createClient(CourierAPIClient.class);
    }
}
