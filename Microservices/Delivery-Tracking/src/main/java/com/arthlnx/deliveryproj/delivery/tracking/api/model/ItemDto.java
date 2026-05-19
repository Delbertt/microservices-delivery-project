package com.arthlnx.deliveryproj.delivery.tracking.api.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    @NotBlank
    private String name;

    @NotBlank
    @Min(1)
    private Integer quantity;
}
