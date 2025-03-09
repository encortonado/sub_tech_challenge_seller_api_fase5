package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO implements Serializable {


    private String cpf;
    private long vehicleId;
}
