package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.mapper;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseEntityDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;

public class PurchaseMapper {
    public static PurchaseEntityDTO toDTO(PurchaseEntity entity) {
        return PurchaseEntityDTO.builder()
                .id(entity.getId())
                .cpf(entity.getCpf())
                .vehicleId(entity.getVehicleId())
                .vehiclePrice(entity.getVehiclePrice())
                .purchaseDate(entity.getPurchaseDate())
                .isPaid(entity.isPaid())
                .status(entity.getStatus())
                .build();
    }

    public static PurchaseEntity toEntity(PurchaseEntityDTO dto) {
        return PurchaseEntity.builder()
                .id(dto.getId())
                .cpf(dto.getCpf())
                .vehicleId(dto.getVehicleId())
                .vehiclePrice(dto.getVehiclePrice())
                .purchaseDate(dto.getPurchaseDate())
                .isPaid(dto.isPaid())
                .status(dto.getStatus())
                .build();
    }
}

