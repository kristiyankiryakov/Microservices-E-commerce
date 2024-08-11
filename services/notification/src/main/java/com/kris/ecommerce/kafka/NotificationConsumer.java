package com.kris.ecommerce.kafka;

import com.kris.ecommerce.email.EmailService;
import com.kris.ecommerce.kafka.order.OrderConfirmation;
import com.kris.ecommerce.kafka.payment.PaymentConfirmation;
import com.kris.ecommerce.notification.Notification;
import com.kris.ecommerce.notification.NotificationRepository;
import com.kris.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation confirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic ::%s", confirmation));

        repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(confirmation)
                        .build()
        );

        var customerName = confirmation.customerFirstname() + " " + confirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                confirmation.customerEmail(),
                customerName,
                confirmation.amount(),
                confirmation.orderReference()
        );

    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation confirmation) throws MessagingException {
        log.info(format("Consuming the message from order-topic Topic ::%s", confirmation));

        repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmaton(confirmation)
                        .build()
        );

        var customerName = confirmation.customer().firstname() + " " + confirmation.customer().lastname();
        emailService.sendOrderConfirmationEmail(
                confirmation.customer().email(),
                customerName,
                confirmation.totalAmount(),
                confirmation.orderReference(),
                confirmation.products()
        );

    }

}
