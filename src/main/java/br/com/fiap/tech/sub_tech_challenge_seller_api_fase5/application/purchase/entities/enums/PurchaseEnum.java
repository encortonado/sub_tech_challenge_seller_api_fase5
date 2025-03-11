package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.enums;

public enum PurchaseEnum {

    INITIATED,        // Compra iniciada
    PAYMENT_PENDING,   // Aguardando pagamento
    PAYMENT_CONFIRMED, // Pagamento confirmado
    COMPLETED,         // Compra concluída com sucesso
    CANCELLED,         // Compra cancelada
    FAILED             // Falha no processo de compra
}

