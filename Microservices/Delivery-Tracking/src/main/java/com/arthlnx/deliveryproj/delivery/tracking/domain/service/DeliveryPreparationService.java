package com.arthlnx.deliveryproj.delivery.tracking.domain.service;

import com.arthlnx.deliveryproj.delivery.tracking.api.modelDto.ContactPointDto;
import com.arthlnx.deliveryproj.delivery.tracking.api.modelDto.DeliveryDto;
import com.arthlnx.deliveryproj.delivery.tracking.api.modelDto.ItemDto;
import com.arthlnx.deliveryproj.delivery.tracking.domain.exception.DomainException;
import com.arthlnx.deliveryproj.delivery.tracking.domain.model.ContactPoint;
import com.arthlnx.deliveryproj.delivery.tracking.domain.model.Delivery;
import com.arthlnx.deliveryproj.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;

    @Transactional
    public Delivery draft(DeliveryDto deliveryDto) {
        Delivery delivery = Delivery.draft();

        handlePreparation(deliveryDto, delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryDto deliveryDto) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException(
                        "Delivery ID " + deliveryId + " not found."));

        delivery.clearItems();
        handlePreparation(deliveryDto, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePreparation(DeliveryDto deliveryDto, Delivery delivery) {
        ContactPointDto senderDto = deliveryDto.getSender();
        ContactPointDto recipientDto = deliveryDto.getRecipient();

        ContactPoint sender = ContactPoint.builder()
                .phone(senderDto.getPhone())
                .name(senderDto.getName())
                .complement(senderDto.getComplement())
                .number(senderDto.getNumber())
                .zipCode(senderDto.getZipCode())
                .street(senderDto.getStreet())
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .phone(recipientDto.getPhone())
                .name(recipientDto.getName())
                .complement(recipientDto.getComplement())
                .number(recipientDto.getNumber())
                .zipCode(recipientDto.getZipCode())
                .street(recipientDto.getStreet())
                .build();

        Duration expectedDeliveryTime = Duration.ofHours(3);
        BigDecimal distanceFee = new BigDecimal("10");

        BigDecimal payout = new BigDecimal("10");

        var preparationDetails = Delivery.PreparationDetails.builder()
                .recipient(recipient)
                .sender(sender)
                .expectedDeliveredAt(expectedDeliveryTime)
                .courierPayout(payout)
                .distanceFee(distanceFee)
                .build();

        delivery.editPreparationDetails(preparationDetails);

        for (ItemDto itemDto : deliveryDto.getItems()) {
            delivery.addItem(itemDto.getName(), itemDto.getQuantity());
        }
    }
}
