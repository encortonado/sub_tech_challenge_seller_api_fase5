package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.enums.PurchaseEnum;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.helpers.CpfMaskSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseEntityDTO {

    private String id;

    @JsonSerialize(using = CpfMaskSerializer.class) // Mantém a máscara no CPF
    private String cpf;

    private long vehicleId;
    private double vehiclePrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date purchaseDate;

    private boolean isPaid;
    private PurchaseEnum status;

}
