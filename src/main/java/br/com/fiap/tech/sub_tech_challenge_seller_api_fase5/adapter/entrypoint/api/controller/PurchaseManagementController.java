package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.controller;


import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseEntityDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.services.VehiclePurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manage")
public class PurchaseManagementController {

    private final VehiclePurchaseService vehiclePurchaseService;

    public PurchaseManagementController(VehiclePurchaseService vehiclePurchaseService) {
        this.vehiclePurchaseService = vehiclePurchaseService;
    }

    @GetMapping
    ResponseEntity<List<PurchaseEntityDTO>> listAllPurchases() {
        return ResponseEntity.ok().body(vehiclePurchaseService.listSoldVehicles());
    }
}
