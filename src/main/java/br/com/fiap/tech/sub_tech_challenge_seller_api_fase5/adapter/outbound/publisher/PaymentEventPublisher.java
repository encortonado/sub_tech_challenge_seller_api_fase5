package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.publisher;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.model.PurchaseEventDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.configuration.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;


    public PaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishPaymentStarted(PurchaseEntity purchase) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_SALES, RabbitMQConfig.QUEUE_PAYMENT_PENDING, purchase);
    }

}
