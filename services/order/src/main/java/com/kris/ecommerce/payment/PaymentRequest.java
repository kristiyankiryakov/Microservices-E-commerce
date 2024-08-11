package com.kris.ecommerce.payment;

import com.kris.ecommerce.customer.CustomerResponse;
import com.kris.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
