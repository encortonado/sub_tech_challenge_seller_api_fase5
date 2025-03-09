package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.services;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.PurchaseEntityDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.api.model.VehicleDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.entrypoint.persistance.VehiclePurchaseRepository;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.model.PurchaseEventDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.publisher.PaymentEventPublisher;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.adapter.outbound.publisher.SaleEventPublisher;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.PurchaseEntity;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.entities.enums.PurchaseEnum;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.application.purchase.ports.IVehiclePurshaceService;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.exceptions.CustomErrorTypeException;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiclePurchaseService implements IVehiclePurshaceService {

    private VehiclePurchaseRepository vehiclePurchaseRepository;
    private SaleEventPublisher saleEventPublisher;
    private PaymentEventPublisher paymentEventPublisher;


    public VehiclePurchaseService(VehiclePurchaseRepository vehiclePurchaseRepository, SaleEventPublisher saleEventPublisher, PaymentEventPublisher paymentEventPublisher) {
        this.vehiclePurchaseRepository = vehiclePurchaseRepository;
        this.saleEventPublisher = saleEventPublisher;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @Value("${vehicle.url}")
    private String vehicleAPI;

    @Override
    public VehicleDTO findVehicle(Long vehicleId) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String vehicleURL = vehicleAPI + '/' + vehicleId;

            URI uri = new URI(vehicleURL);

            return restTemplate.exchange(
                    uri, HttpMethod.GET, HttpEntity.EMPTY, VehicleDTO.class).getBody();

        } catch (HttpClientErrorException ex) {
            throw new CustomErrorTypeException("nao encontrado");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<PurchaseEntityDTO> listSoldVehicles() {
        List<PurchaseEntity> list = vehiclePurchaseRepository.findAllByIsPaidIsTrueOrderByVehiclePriceDesc();

        return list.stream()
                .map(PurchaseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void allowPayment(String purchaseId) {
        PurchaseEntity purchase = findById(purchaseId);
        purchase.setPaid(true);
        purchase.setStatus(PurchaseEnum.PAYMENT_CONFIRMED);
        saleEventPublisher.publishSaleFinished(new PurchaseEventDTO(purchase.getVehicleId()));
        purchase.setStatus(PurchaseEnum.COMPLETED);
        vehiclePurchaseRepository.save(purchase);
    }

    public PurchaseEntity findById(String id) {

        return vehiclePurchaseRepository.findById(id).orElseThrow(() -> new CustomErrorTypeException("nao encontrado"));
    }

    @Override
    public PurchaseEntityDTO sellVehicle(PurchaseDTO purchaseDTO) {
        VehicleDTO vehicle = findVehicle(purchaseDTO.getVehicleId());

        if (vehicle.isHold()) {
            throw new CustomErrorTypeException("Veiculo está indisponivel");
        }

        saleEventPublisher.publishSaleStarted(
                new PurchaseEventDTO(purchaseDTO.getVehicleId()));

        PurchaseEntity purchase = PurchaseEntity.builder()
                .isPaid(false)
                .cpf(purchaseDTO.getCpf())
                .purchaseDate(new Date())
                .vehicleId(purchaseDTO.getVehicleId())
                .vehiclePrice(vehicle.getPrice())
                .status(PurchaseEnum.INITIATED)
                .build();

        paymentEventPublisher.publishPaymentStarted(purchase);
        purchase.setStatus(PurchaseEnum.PAYMENT_PENDING);

        vehiclePurchaseRepository.save(purchase);

        return PurchaseMapper.toDTO(purchase);
    }

    @Override
    public PurchaseEntityDTO findSaleById(String id) {
        Optional<PurchaseEntity> purchaseEntity = vehiclePurchaseRepository.findById(id);

        return PurchaseMapper.toDTO(
                        purchaseEntity.orElseThrow(
                                () -> new CustomErrorTypeException("compra nao encontrada")));

    }
}
