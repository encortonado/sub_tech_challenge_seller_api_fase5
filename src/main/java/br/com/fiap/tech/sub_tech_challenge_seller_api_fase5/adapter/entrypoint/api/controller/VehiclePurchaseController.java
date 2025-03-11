package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.controller;


import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseEntityDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.services.VehiclePurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/purchase")

public class VehiclePurchaseController {

    private final VehiclePurchaseService vehiclePurchaseService;

    public VehiclePurchaseController(VehiclePurchaseService vehiclePurchaseService) {
        this.vehiclePurchaseService = vehiclePurchaseService;
    }

    @GetMapping("/status/{id}")
    ResponseEntity<PurchaseEntityDTO> followSale(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(vehiclePurchaseService.findSaleById(id));
    }

    @GetMapping("/{id}")
    ResponseEntity<String> allowPayment(@PathVariable("id") String id) {
        vehiclePurchaseService.allowPayment(id);
        return ResponseEntity.ok().body("Confirmação de Pagamento em processamento.");
    }

    @PostMapping
    ResponseEntity<PurchaseEntityDTO> sellVehicle(@RequestBody PurchaseDTO purchase) {
        return ResponseEntity.ok().body(vehiclePurchaseService.sellVehicle(purchase));
    }

}
