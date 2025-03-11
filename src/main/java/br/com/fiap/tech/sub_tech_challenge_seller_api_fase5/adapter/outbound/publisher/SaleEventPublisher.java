package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.publisher;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.model.PurchaseEventDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.configuration.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaleEventPublisher {

    private final RabbitTemplate rabbitTemplate;


    public SaleEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishSaleStarted(PurchaseEventDTO purchaseDTO) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_SALES, RabbitMQConfig.QUEUE_SALE_STARTED, purchaseDTO);
    }

    public void publishSaleFinished(PurchaseEventDTO purchaseDTO) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_SALES, RabbitMQConfig.QUEUE_SALE_COMPLETED, purchaseDTO);
    }

}
