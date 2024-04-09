package br.com.kartstore.service;

import br.com.kartstore.controller.sale.dto.Sale;
import br.com.kartstore.service.database.entity.SaleEntity;
import br.com.kartstore.service.database.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindSaleServiceTest {
    @InjectMocks
    private FindSaleService findSaleService;

    @Mock
    private SaleRepository saleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByNumControlIn() {
        // Mock data
        List<String> controls = Arrays.asList("ABC123", "DEF456");
        List<SaleEntity> saleEntities = Arrays.asList(
                new SaleEntity(1, "ABC123", new Date(), "Product A", BigDecimal.valueOf(100), 5L, 1, null, null),
                new SaleEntity(2, "DEF456", new Date(), "Product B", BigDecimal.valueOf(150), 3L, 2, null, null)
        );

        // Mock behavior
        Mockito.when(saleRepository.findByNumControlIn(controls)).thenReturn(saleEntities);

        // Perform the test
        List<Sale> sales = findSaleService.findByNumControlIn(controls);

        // Verify that the repository method was called with the expected parameters
        Mockito.verify(saleRepository).findByNumControlIn(controls);

        // Verify the result
        assertEquals(2, sales.size());
        assertEquals("ABC123", sales.get(0).getNumControl());
        assertEquals("DEF456", sales.get(1).getNumControl());
    }

    @Test
    public void testFindAll() {
        // Mock data
        Long saleId = 1L;
        Date createdStart = new Date();
        Date createdEnd = new Date();
        Pageable pageable = PageRequest.of(0, 10);
        List<SaleEntity> entitySales = Arrays.asList(
                new SaleEntity(1, "ABC123", new Date(), "Product A", BigDecimal.valueOf(100), 5L, 1, null, null),
                new SaleEntity(2, "DEF456", new Date(), "Product B", BigDecimal.valueOf(150), 3L, 2, null, null)
        );
        Page<SaleEntity> page = new PageImpl<>(entitySales);

        // Mock behavior
        Mockito.when(saleRepository.findSales(saleId, createdStart, createdEnd, pageable)).thenReturn(page);

        // Perform the test
        Page<Sale> result = findSaleService.findAll(saleId, createdStart, createdEnd, pageable);

        // Verify that the repository method was called with the expected parameters
        Mockito.verify(saleRepository).findSales(saleId, createdStart, createdEnd, pageable);

        // Verify the result
        assertEquals(2, result.getTotalElements());
        assertEquals("ABC123", result.getContent().get(0).getNumControl());
        assertEquals("DEF456", result.getContent().get(1).getNumControl());
    }
}
