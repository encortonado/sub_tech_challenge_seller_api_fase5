package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.ports;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseEntityDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;

public interface ISellVehicle {

    PurchaseEntityDTO sellVehicle(PurchaseDTO purchaseDTO);
}
