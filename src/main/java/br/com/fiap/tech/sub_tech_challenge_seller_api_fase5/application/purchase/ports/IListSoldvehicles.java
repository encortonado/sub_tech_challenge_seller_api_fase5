package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.ports;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;

import java.util.List;

public interface IListSoldvehicles {

    List<PurchaseEntity> listSoldVehicles();
}
