package com.kris.ecommerce.kafka;

import com.kris.ecommerce.customer.CustomerResponse;
import com.kris.ecommerce.order.PaymentMethod;
import com.kris.ecommerce.product.PurchaseResponse;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Validated
public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
