package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.persistance;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiclePurchaseRepository extends MongoRepository<PurchaseEntity, String> {

    @Query("{'isPaid':  true}")
    List<PurchaseEntity> findAllByIsPaidIsTrueOrderByVehiclePriceDesc();




}
