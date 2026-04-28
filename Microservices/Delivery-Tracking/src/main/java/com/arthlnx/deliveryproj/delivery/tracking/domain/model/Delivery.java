package com.arthlnx.deliveryproj.delivery.tracking.domain.model;

import com.arthlnx.deliveryproj.delivery.tracking.domain.exception.DomainException;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter(AccessLevel.PRIVATE)
@Getter
public class Delivery {
    @EqualsAndHashCode.Include
    private UUID id;

    private UUID courierId;

    private DeliveryStatus status;

    private OffsetDateTime placedAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveredAt;
    private OffsetDateTime fullFilledAt;

    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;

    private Integer totalItems;

    private ContactPoint sender;
    private ContactPoint recipient;

    private List<Item> items = new ArrayList<>();

    // Factory for delivery on draft status
    public static Delivery draft() {
        Delivery delivery = new Delivery();
        delivery.setId(UUID.randomUUID());
        delivery.setStatus(DeliveryStatus.DRAFT);
        delivery.setTotalItems(0);
        delivery.setTotalCost(BigDecimal.ZERO);
        delivery.setCourierPayout(BigDecimal.ZERO);
        delivery.setDistanceFee(BigDecimal.ZERO);

        return delivery;
    }

    public UUID addItem(String itemName, int quantity) {
        Item item = Item.brandNew(itemName, quantity);
        this.items.add(item);

        return item.getId();
    }

    public void removeItem(UUID itemId) {
        this.items.removeIf(item -> item.getId().equals(itemId));
        this.calculateTotalItems();
    }

    public void changeItemQuantity(UUID itemId, int quantity) {
        Item item = this.getItems().stream().filter(
                i -> i.getId().equals(itemId)).findFirst(
        ).orElseThrow();

        item.setQuantity(quantity);
        this.calculateTotalItems();
    }

    public void clearItems() {
        this.items.clear();
        this.calculateTotalItems();
    }

    public void editPreparationDetails(PreparationDetails preparationDetails) {
        this.verifyIfCanBeEdited();

        this.setSender(preparationDetails.getSender());
        this.setRecipient(preparationDetails.getRecipient());
        this.setDistanceFee(preparationDetails.getDistanceFee());
        this.setCourierPayout(preparationDetails.getCourierPayout());

        this.setExpectedDeliveredAt(OffsetDateTime.now().plus(preparationDetails.expectedDeliveredAt));
        this.setTotalCost(this.getDistanceFee().add(this.getCourierPayout()));
    }

    public void place() {
        this.verifyIfCanBePlaced();
        this.changeStatusTo(DeliveryStatus.WAITING_FOR_COURIER);
        this.setPlacedAt(OffsetDateTime.now());
    }

    public void pickup(UUID courierId) {
        this.setCourierId(courierId);
        this.changeStatusTo(DeliveryStatus.IN_TRANSIT);
        this.setAssignedAt(OffsetDateTime.now());
    }

    public void markDelivered() {
        this.changeStatusTo(DeliveryStatus.DELIVERED);
        this.setAssignedAt(OffsetDateTime.now());
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items);
    };

    private void calculateTotalItems() {
        int totalItems = items.stream().mapToInt(Item::getQuantity).sum();
        setTotalItems(totalItems);
    }

    private void  verifyIfCanBePlaced() {
        if (!this.isFilled()) {
            throw new DomainException();
        }

        if (!this.getStatus().equals(DeliveryStatus.DRAFT)) {
            throw new DomainException();
        }
    }

    private boolean isFilled() {
        return this.getSender() != null
                && this.getRecipient() != null
                && this.totalCost != null;
    }

    private void verifyIfCanBeEdited() {
        if (!this.getStatus().equals(DeliveryStatus.DRAFT)) {
            throw new DomainException();
        }
    }

    private void changeStatusTo(DeliveryStatus newStatus) {
        if (newStatus != null  && this.getStatus().canChangeTo(newStatus)) {
            throw new DomainException(
                    "Invalid status change: "
                            + this.getStatus()
                            + " to " + newStatus);
        }
        this.setStatus(newStatus);
    }

    @Getter
    @AllArgsConstructor()
    @Builder
    public static class PreparationDetails {
        private ContactPoint sender;
        private ContactPoint recipient;
        private BigDecimal distanceFee;
        private BigDecimal courierPayout;
        private Duration expectedDeliveredAt;
    }
}
