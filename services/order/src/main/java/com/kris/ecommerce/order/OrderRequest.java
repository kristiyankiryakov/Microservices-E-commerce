package com.kris.ecommerce.order;

import com.kris.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount must be positive.")
        BigDecimal amount,
        @NotNull(message = "Order payment method must not be null.")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer must be present.")
        @NotEmpty(message = "Customer must be present.")
        @NotBlank(message = "Customer must be present.")
        String customerId,
        @NotEmpty(message = "At least one product must be present.")
        List<PurchaseRequest> products
) {
}
