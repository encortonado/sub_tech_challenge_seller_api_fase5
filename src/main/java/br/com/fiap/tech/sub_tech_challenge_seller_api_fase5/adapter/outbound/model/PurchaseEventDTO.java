package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseEventDTO implements Serializable {

    private long vehicleId;

}
