package com.arthlnx.deliveryproj.delivery.tracking.infrastructure.event;

import com.arthlnx.deliveryproj.delivery.tracking.domain.event.DeliveryFullfiledEvent;
import com.arthlnx.deliveryproj.delivery.tracking.domain.event.DeliveryPickedUpEvent;
import com.arthlnx.deliveryproj.delivery.tracking.domain.event.DeliveryPlacedEvent;
import com.arthlnx.deliveryproj.delivery.tracking.infrastructure.kafka.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @EventListener
    public void handle(DeliveryPlacedEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(
                event,
                event.getDeliveryId().toString(),
                KafkaTopicConfig.deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryPickedUpEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(
                event,
                event.getDeliveryId().toString(),
                KafkaTopicConfig.deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryFullfiledEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(
                event,
                event.getDeliveryId().toString(),
                KafkaTopicConfig.deliveryEventsTopicName);
    }
}
