package br.com.kartstore.controller.sale;

import br.com.kartstore.controller.sale.dto.Sale;
import br.com.kartstore.service.CreateSaleService;
import br.com.kartstore.service.FindSaleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaleControllerTest {

    @InjectMocks
    private SaleController controller;

    @Mock
    private CreateSaleService cService;

    @Mock
    private FindSaleService fService;

    private AutoCloseable closeable;


    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void afterEach() throws Exception {
        closeable.close();
    }

    @Test
    public void testCreate() {
        List<Sale> sales = new ArrayList<>();
        Mockito.doNothing().when(cService).create(sales);

        ResponseEntity<List<Sale>> responseEntity = controller.create(sales);

        Mockito.verify(cService).create(sales);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sales, responseEntity.getBody());
    }

    @Test
    public void testFind() {
        Long saleId = 0L;
        Date createdStart = new Date();
        Date createdEnd = new Date();
        Pageable pageable = PageRequest.of(0, 8);;

        Page<Sale> sales = Mockito.mock(Page.class);
        Mockito.when(fService.findAll(saleId, createdStart, createdEnd, pageable)).thenReturn(sales);

        ResponseEntity<Page<Sale>> responseEntity = controller.find(saleId, createdStart, createdEnd, pageable);

        Mockito.verify(fService, Mockito.times(1)).findAll(saleId, createdStart, createdEnd, pageable);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sales, responseEntity.getBody());
    }

}
