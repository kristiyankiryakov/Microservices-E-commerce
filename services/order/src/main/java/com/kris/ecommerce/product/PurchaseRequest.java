package com.kris.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Porduct is mandatory.")
        Integer productId,
        @Positive(message = "Quantity is mandatory.")
        double quantity
) {
}
