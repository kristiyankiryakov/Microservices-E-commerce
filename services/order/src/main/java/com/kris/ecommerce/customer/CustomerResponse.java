package com.kris.ecommerce.customer;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        @NotNull
        String email
) {
}
