package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.controller;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseEntityDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.services.VehiclePurchaseService;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.exceptions.CustomErrorTypeException;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.handler.GlobalExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VehiclePurchaseControllerTest {

    private MockMvc mockMvc;
    AutoCloseable mock;

    @Mock
    VehiclePurchaseService vehiclePurchaseService;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        VehiclePurchaseController vehicleController = new VehiclePurchaseController(vehiclePurchaseService);

        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void listAllPurchases() throws Exception {

        List<PurchaseEntityDTO> vehicles = purchasesListDTO();

        when(vehiclePurchaseService.listSoldVehicles()).thenReturn(vehicles);

        mockMvc.perform(get("/api/purchase"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(vehiclePurchaseService, times(1)).listSoldVehicles();

    }

    @Test
    void shouldAllowPayment() throws Exception {
        String purchaseId = "123";

        doNothing().when(vehiclePurchaseService).allowPayment(purchaseId);


        mockMvc.perform(get("/api/purchase/{id}", purchaseId))
                .andExpect(status().isOk())
                .andExpect(content().string("Confirmação de Pagamento em processamento."));  // Verifica o corpo da resposta


        verify(vehiclePurchaseService, times(1)).allowPayment(purchaseId);
    }

    @Test
    void shouldThrowExceptionWhenPurchaseNotFound() throws Exception {
        // Arrange: Configura o serviço para lançar a exceção quando a compra não for encontrada
        String invalidPurchaseId = "999";

        doThrow(new CustomErrorTypeException("compra nao encontrada"))
                .when(vehiclePurchaseService).allowPayment(invalidPurchaseId);

        // Act & Assert: Faz a chamada GET e verifica o status e corpo da resposta
        mockMvc.perform(get("/api/purchase/{id}", invalidPurchaseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())  // Verifica se o status é 404
                .andExpect(jsonPath("$.errorType").value(404))  // Verifica o tipo de erro no corpo da resposta
                .andExpect(jsonPath("$.message").value("compra nao encontrada"));  // Verifica a mensagem

        // Verifica se o serviço foi chamado uma vez com o ID inválido
        verify(vehiclePurchaseService, times(1)).allowPayment(invalidPurchaseId);
    }

    @Test
    void shouldSellVehicle() throws Exception {

        PurchaseDTO purchaseDTO = new PurchaseDTO("123", 56);
        PurchaseEntityDTO purchaseEntity = purchasesListDTO().get(0);
        purchaseEntity.setId("789");

        when(vehiclePurchaseService.sellVehicle(purchaseDTO)).thenReturn(purchaseEntity);

        mockMvc.perform(post("/api/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(purchaseDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("789"))
                .andExpect(jsonPath("$.vehicleId").value(1))
                .andExpect(jsonPath("$.cpf").value("1234567890"))
                .andExpect(jsonPath("$.vehiclePrice").value(50000.0));


        verify(vehiclePurchaseService, times(1)).sellVehicle(purchaseDTO);
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
                .isPaid(true)
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

    public static String asJsonString(final Object obj) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();

        om.registerModule(new JavaTimeModule());

        return om.writeValueAsString(obj);
    }

}