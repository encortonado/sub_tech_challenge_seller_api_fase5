package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.ports;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.VehicleDTO;

public interface IFindVehicle {

    VehicleDTO findVehicle(Long vehicleId);

}
