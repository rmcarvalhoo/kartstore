package br.com.kartstore.service.validation;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.kartstore.controller.sale.dto.ClientDTO;
import br.com.kartstore.controller.sale.dto.Sale;
import br.com.kartstore.exception.ClientException;
import br.com.kartstore.exception.SaleException;
import br.com.kartstore.service.FindClientService;
import br.com.kartstore.service.FindSaleService;
import br.com.kartstore.service.database.entity.ClientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

public class SaleValidationTest {

    @InjectMocks
    private SaleValidation saleValidation;

    @Mock
    private FindSaleService findSaleService;

    @Mock
    private FindClientService findClientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSaleValidation_NumOfSaleWithinLimit() {
        List<Sale> sales = Arrays.asList(getSale("1a", new Date(), "Oleo", BigDecimal.ONE, 2L, 2),
                getSale("2a", new Date(), "Corrente", BigDecimal.ONE, 2L, 4),
                getSale("3a", new Date(), "Catraca", BigDecimal.TEN, 1L, 3));

        List<Integer> clientIds = Arrays.asList(2,4,3);
        List<ClientDTO> entitys = Arrays.asList(mapClient(2, "Ana", "123", "a@agmail.com"),
                mapClient(4, "Bob", "456", "b@agmail.com"),
                mapClient(3, "Carol", "789", "c@agmail.com"));

        Mockito.when(findClientService.findClientIds(clientIds)).thenReturn(entitys);
        saleValidation.createSaleValidation(sales);
    }

    @Test
    public void testCreateSaleValidation_NumOfSaleExceedsLimit() {
        // Mock data
        List<Sale> sales = Arrays.asList(new Sale(), new Sale(), new Sale(), new Sale(), new Sale(), new Sale(), new Sale(), new Sale(), new Sale(), new Sale(), new Sale());

        // Perform the test and verify the exception
        assertThrows(SaleException.class, () -> saleValidation.createSaleValidation(sales));
    }

    @Test
    public void testCreateSaleValidation_NumControlExists() {
        // Mock data
        Sale sale1 = new Sale();
        sale1.setNumControl("ABC123");
        Sale sale2 = new Sale();
        sale2.setNumControl("DEF456");
        List<Sale> sales = Arrays.asList(sale1, sale2);

        // Mock behavior
        when(findSaleService.findByNumControlIn(Arrays.asList("ABC123", "DEF456"))).thenReturn(Arrays.asList(new Sale(), new Sale()));

        // Perform the test and verify the exception
        assertThrows(SaleException.class, () -> saleValidation.createSaleValidation(sales));
    }

    @Test
    public void testCreateSaleValidation_ClientIdsNotFound() {
        // Mock data
        Sale sale1 = new Sale();
        sale1.setClientId(1);
        Sale sale2 = new Sale();
        sale2.setClientId(2);
        List<Sale> sales = Arrays.asList(sale1, sale2);

        // Mock behavior
        when(findClientService.findClientIds(Arrays.asList(1, 2))).thenReturn(Collections.singletonList(new ClientDTO()));

        // Perform the test and verify the exception
        assertThrows(ClientException.class, () -> saleValidation.createSaleValidation(sales));
    }

    @Test
    public void testCreateSaleValidation_ClientIdsFound() {
        // Mock data
        Sale sale1 = new Sale();
        sale1.setClientId(1);
        Sale sale2 = new Sale();
        sale2.setClientId(2);
        List<Sale> sales = Arrays.asList(sale1, sale2);

        when(findClientService.findClientIds(Arrays.asList(1, 2))).thenReturn(Arrays.asList(mapClient(1, "Ana", "123", "a@agmail.com"),
                mapClient(2, "Ana", "123", "a@agmail.com")));

        saleValidation.createSaleValidation(sales);


    }


    private Sale getSale(String numControl, Date created, String productName, BigDecimal productValue, Long amount, Integer clientId){
        return Sale.builder()
            .numControl(numControl)
            .created(new Date())
            .productName(productName)
            .productValue(productValue)
            .amount(amount)
            .clientId(clientId)
            .build();
    }

    private ClientDTO mapClient(Integer id, String name, String cpf, String email) {
        ClientDTO e = new ClientDTO();
        e.setId(id);
        e.setName(name);
        e.setCpf(cpf);
        e.setEmail(email);
        return e;
    }

}
