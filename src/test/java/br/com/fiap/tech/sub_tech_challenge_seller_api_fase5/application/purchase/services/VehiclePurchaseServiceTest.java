package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.services;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseEntityDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.persistance.VehiclePurchaseRepository;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.publisher.PaymentEventPublisher;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.publisher.SaleEventPublisher;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "vehicle.url=http://localhost:8080/api/vehicle/")
class VehiclePurchaseServiceTest {


    private VehiclePurchaseService vehiclePurchaseService;

    @Mock
    private VehiclePurchaseRepository vehiclePurchaseRepository;

    @Mock
    private SaleEventPublisher saleEventPublisher;

    @Mock
    private PaymentEventPublisher paymentEventPublisher;


    @Mock
    private RestTemplate restTemplate;

    AutoCloseable openMocks;

    @BeforeEach()
    void setup() {
        vehiclePurchaseService =
                new VehiclePurchaseService(
                        vehiclePurchaseRepository,
                        saleEventPublisher,
                        paymentEventPublisher);
    }

    @AfterEach
    void tearDown() throws Exception{
        
    }




    @Test
    void listSoldVehicles() {

        List<PurchaseEntity> list = purchasesList();

        List<PurchaseEntityDTO> list2 = purchasesListDTO();

        when(vehiclePurchaseRepository.findAllByIsPaidIsTrueOrderByVehiclePriceDesc()).thenReturn(list);

        List<PurchaseEntityDTO> result = vehiclePurchaseService.listSoldVehicles();

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(list2.get(0), list2.get(1), list2.get(2));

        verify(vehiclePurchaseRepository, times(1)).findAllByIsPaidIsTrueOrderByVehiclePriceDesc();
    }

    @Test
    void allowPayment() {
        PurchaseEntity purchase = PurchaseEntity.builder()
                .id("123")
                .vehicleId(123)
                .vehiclePrice(1222.00)
                .purchaseDate(new Date())
                .cpf("123345")
                .isPaid(false)
                .build();

        when(vehiclePurchaseRepository.findById("123")).thenReturn(Optional.of(purchase));

        vehiclePurchaseService.allowPayment("123");

        verify(vehiclePurchaseRepository, times(1)).findById(any());
    }

    @Test
    void findById() {
        PurchaseEntity purchase = PurchaseEntity.builder()
                .id("123")
                .vehicleId(123)
                .vehiclePrice(1222.00)
                .purchaseDate(new Date())
                .cpf("123345")
                .isPaid(false)
                .build();

        when(vehiclePurchaseRepository.findById("123")).thenReturn(Optional.of(purchase));

        vehiclePurchaseService.findById("123");

        assertThat(purchase).isNotNull();
        verify(vehiclePurchaseRepository, times(1)).findById(any());

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
                .isPaid(false)
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

    List<PurchaseEntityDTO> purchasesListDTO() {
        var purchase1 = PurchaseEntityDTO.builder()
                .cpf("1234567890")
                .purchaseDate(new Date())
                .vehicleId(1)
                .vehiclePrice(50000.0)
                .isPaid(true)
                .build();

        var purchase2 = PurchaseEntityDTO.builder()
                .cpf("0987654321")
                .purchaseDate(new Date())
                .vehicleId(2)
                .vehiclePrice(30000.0)
                .isPaid(false)
                .build();

        var purchase3 = PurchaseEntityDTO.builder()
                .cpf("1112223334")
                .purchaseDate(new Date())
                .vehicleId(3)
                .vehiclePrice(20000.0)
                .isPaid(true)
                .build();

        return Arrays.asList(purchase1, purchase2, purchase3);
    }

}