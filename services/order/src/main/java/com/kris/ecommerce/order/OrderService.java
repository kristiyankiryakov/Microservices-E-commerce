package com.kris.ecommerce.order;

import com.kris.ecommerce.customer.CustomerClient;
import com.kris.ecommerce.exception.BusinessException;
import com.kris.ecommerce.orderline.OrderLineRequest;
import com.kris.ecommerce.orderline.OrderLineService;
import com.kris.ecommerce.product.ProductClient;
import com.kris.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest request) {

        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with provided ID"));

        this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

            //todo: start payment method

        return null;
    }

}
