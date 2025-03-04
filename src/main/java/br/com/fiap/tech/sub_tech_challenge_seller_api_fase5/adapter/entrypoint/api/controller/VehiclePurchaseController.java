package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.controller;


import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.services.VehiclePurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")

public class VehiclePurchaseController {

    private final VehiclePurchaseService vehiclePurchaseService;

    public VehiclePurchaseController(VehiclePurchaseService vehiclePurchaseService) {
        this.vehiclePurchaseService = vehiclePurchaseService;
    }


    @GetMapping
    ResponseEntity<List<PurchaseEntity>> listAllPurchases() {
        return ResponseEntity.ok().body(vehiclePurchaseService.listSoldVehicles());
    }

    @GetMapping("/{id}")
    ResponseEntity<String> allowPayment(@PathVariable("id") String id) {

        vehiclePurchaseService.allowPayment(id);

        return ResponseEntity.ok().body("Confirmação de Pagamento em processamento.");
    }

    @PostMapping
    ResponseEntity<PurchaseEntity> sellVehicle(@RequestBody PurchaseDTO purchase) {


        return ResponseEntity.ok().body(vehiclePurchaseService.sellVehicle(purchase));
    }

}
