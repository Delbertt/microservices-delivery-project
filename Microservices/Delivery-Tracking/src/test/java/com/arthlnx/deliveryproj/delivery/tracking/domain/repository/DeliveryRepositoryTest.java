package com.arthlnx.deliveryproj.delivery.tracking.domain.repository;

import com.arthlnx.deliveryproj.delivery.tracking.domain.model.ContactPoint;
import com.arthlnx.deliveryproj.delivery.tracking.domain.model.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void shouldPersist() {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createValidPreparationDetails());

        delivery.addItem("Computador",  2);
        delivery.addItem("Notebook",  2);

        deliveryRepository.saveAndFlush(delivery);

        Delivery persistedDelivery = deliveryRepository.findById(
                delivery.getId()).orElseThrow();

        assertEquals(2, persistedDelivery.getItems().size());
    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender = ContactPoint.builder()
                .zipCode("00000-000")
                .street("Rua Teste")
                .number("12345")
                .complement("Sala Teste")
                .name("Fulano Teste")
                .phone("4499999999")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("12345-000")
                .street("Rua Teste 2")
                .number("123")
                .complement("Lado T")
                .name("Teste Testando")
                .phone("4199999999")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("2.00"))
                .expectedDeliveredAt(Duration.ofHours(1))
                .build();
    }

}