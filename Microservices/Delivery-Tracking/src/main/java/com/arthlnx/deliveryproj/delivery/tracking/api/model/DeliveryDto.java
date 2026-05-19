package com.arthlnx.deliveryproj.delivery.tracking.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryDto {

    @NotNull
    @Valid
    private ContactPointDto sender;
    @NotNull
    @Valid
    private ContactPointDto recipient;

    @NotNull
    @Valid
    @Size(min = 1)
    private List<ItemDto> items;
}
