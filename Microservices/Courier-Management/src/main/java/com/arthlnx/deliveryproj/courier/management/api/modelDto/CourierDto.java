package com.arthlnx.deliveryproj.courier.management.api.modelDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierDto {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;
}
