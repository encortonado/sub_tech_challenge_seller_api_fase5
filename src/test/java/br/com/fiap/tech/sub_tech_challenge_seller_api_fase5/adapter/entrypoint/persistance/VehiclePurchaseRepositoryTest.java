package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.persistance;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VehiclePurchaseRepositoryTest {

    @Mock
    private VehiclePurchaseRepository vehiclePurchaseRepository;

    AutoCloseable openMocks;

    @BeforeEach()
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }



    @Nested
    class VehicleRepo {

        @Test
        void savePurchase() {

            var purchase = PurchaseEntity.builder()
                    .cpf("1234567890")
                    .purchaseDate(new Date())
                    .vehicleId(123)
                    .isPaid(false)
                    .build();

            when(vehiclePurchaseRepository.save(purchase)).thenReturn(purchase);

            var vehiclePurchased = vehiclePurchaseRepository.save(purchase);

            Assertions.assertThat(vehiclePurchased)
                    .isNotNull()
                    .isInstanceOf(PurchaseEntity.class)
                    .isEqualTo(purchase);

            verify(vehiclePurchaseRepository, times(1)).save(any(PurchaseEntity.class));

        }

        @Test
        void findAllByIsPaidIsTrueOrderByVehiclePriceDesc() {

            List<PurchaseEntity> list = purchasesList();

            when(vehiclePurchaseRepository.findAllByIsPaidIsTrueOrderByVehiclePriceDesc()).thenReturn(list);

            List<PurchaseEntity> result = vehiclePurchaseRepository.findAllByIsPaidIsTrueOrderByVehiclePriceDesc();

            Assertions.assertThat(result)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(3)
                    .containsExactly(list.get(0), list.get(1), list.get(2));

            verify(vehiclePurchaseRepository, times(1)).findAllByIsPaidIsTrueOrderByVehiclePriceDesc();
        }
    }


    List<PurchaseEntity> purchasesList() {
        var purchase1 = PurchaseEntity.builder()
                .cpf("1234567890")
                .purchaseDate(new Date())
                .vehicleId(1)
                .vehiclePrice(50000.0)
                .isPaid(true)
                .build();

        var purchase2 = PurchaseEntity.builder()
                .cpf("0987654321")
                .purchaseDate(new Date())
                .vehicleId(2)
                .vehiclePrice(30000.0)
                .isPaid(true)
                .build();

        var purchase3 = PurchaseEntity.builder()
                .cpf("1112223334")
                .purchaseDate(new Date())
                .vehicleId(3)
                .vehiclePrice(20000.0)
                .isPaid(true)
                .build();

        return Arrays.asList(purchase1, purchase2, purchase3);
    }

}