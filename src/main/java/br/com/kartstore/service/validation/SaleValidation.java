package br.com.kartstore.service.validation;

import br.com.kartstore.controller.sale.dto.ClientDTO;
import br.com.kartstore.controller.sale.dto.Sale;
import br.com.kartstore.exception.ClientException;
import br.com.kartstore.exception.SaleException;
import br.com.kartstore.service.FindClientService;
import br.com.kartstore.service.FindSaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SaleValidation {

    private static final Long LIMIT_OF_SALE = 10L;

    @Autowired
    private FindSaleService findSaleService;

    @Autowired
    private FindClientService findClientService;

    public void createSaleValidation(List<Sale> sales){
        numOfSale(sales);
        existsByNumControl(sales);
        validClientId(sales);
    }

    private void numOfSale(List<Sale> sales) {
        if (sales.size() > LIMIT_OF_SALE){
            throw new SaleException(String.format("ERROR!!! The limit of sale are %d ", LIMIT_OF_SALE));
        }
    }

    private void existsByNumControl(List<Sale> sales) {
        List<String> controls = sales.stream().map(Sale::getNumControl).collect(Collectors.toList());
        log.debug("Verify if number of control [{}] already exist", controls);
        List<Sale> salesByNumControl = findSaleService.findByNumControlIn(controls);
        if (salesByNumControl != null && salesByNumControl.size() > 0) {
            List<String> existsControls = salesByNumControl.stream().map(Sale::getNumControl).collect(Collectors.toList());
            throw new SaleException(String.format("ERROR!!! The number of control %s already exist", existsControls));
        }
        log.debug("The number of control [{}] not exist", controls);
    }

    private void validClientId(List<Sale> sales) {
        List<Integer> clientIds = sales.stream().map(Sale::getClientId).collect(Collectors.toList());
        log.debug("Find client by id [{}]", clientIds);
        List<ClientDTO> clients = findClientService.findClientIds(clientIds);
        if (clients == null || !clients.containsAll(clientIds.stream().map(c -> ClientDTO.builder().id(c).build()).toList())) {
            clientIds.removeAll(clients.stream().map(ClientDTO::getId).collect(Collectors.toList()));
            throw new ClientException(String.format("The client %s not found", clientIds));
        }
        log.debug("found the client [{}]", clients);
    }

}
