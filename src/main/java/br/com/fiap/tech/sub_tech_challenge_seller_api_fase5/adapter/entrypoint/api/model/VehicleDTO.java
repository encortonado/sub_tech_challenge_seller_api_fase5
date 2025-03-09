package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private Long id;

    private String brand;

    private String model;

    private String color;

    private int year;

    private double price;

    private boolean isHold;
}
