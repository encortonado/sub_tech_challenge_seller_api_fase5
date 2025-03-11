package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_SALES = "sales-exchange";
    public static final String QUEUE_SALE_STARTED = "sale.started.queue";
    public static final String QUEUE_PAYMENT_PENDING = "payment.pending.queue";
    public static final String QUEUE_SALE_COMPLETED = "sale.completed.queue";

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange salesExchange() {
        return new TopicExchange(EXCHANGE_SALES);
    }


    @Bean
    public Queue saleStartedQueue() {
        return new Queue(QUEUE_SALE_STARTED);
    }

    @Bean
    public Queue paymentConfirmedQueue() {
        return new Queue(QUEUE_PAYMENT_PENDING);
    }

    @Bean
    public Queue saleCompletedQueue() {
        return new Queue(QUEUE_SALE_COMPLETED);
    }

    @Bean
    public Binding bindSaleStartedQueue(Queue saleStartedQueue, TopicExchange salesExchange) {
        return BindingBuilder.bind(saleStartedQueue).to(salesExchange).with(QUEUE_SALE_STARTED);
    }

    @Bean
    public Binding bindPaymentConfirmedQueue(Queue paymentConfirmedQueue, TopicExchange salesExchange) {
        return BindingBuilder.bind(paymentConfirmedQueue).to(salesExchange).with(QUEUE_PAYMENT_PENDING);
    }

    @Bean
    public Binding bindSaleCompletedQueue(Queue saleCompletedQueue, TopicExchange salesExchange) {
        return BindingBuilder.bind(saleCompletedQueue).to(salesExchange).with(QUEUE_SALE_COMPLETED);
    }

}
