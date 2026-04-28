package com.arthlnx.deliveryproj.delivery.tracking.domain.model;

import com.arthlnx.deliveryproj.delivery.tracking.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeStatusToPlaced() {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(
                createValidPreparationDetails()
        );
        delivery.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotChangeStatusToPlaced() {
        Delivery delivery = Delivery.draft();

        assertThrows(DomainException.class, () -> {
            delivery.place();
        });
        assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
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