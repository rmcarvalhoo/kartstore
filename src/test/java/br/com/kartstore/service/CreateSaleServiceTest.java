package br.com.kartstore.service;

import br.com.kartstore.controller.sale.dto.Sale;
import br.com.kartstore.service.database.entity.SaleEntity;
import br.com.kartstore.service.database.repository.SaleRepository;
import br.com.kartstore.service.strategy.CalcContext;
import br.com.kartstore.service.strategy.enums.CalcType;
import br.com.kartstore.service.validation.SaleValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreateSaleServiceTest {
    @InjectMocks
    private CreateSaleService createSaleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleValidation saleValidation;

    @Mock
    private CalcContext calcContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setAmount(10L);
        sale1.setProductValue(BigDecimal.valueOf(50L));
        sale1.setCreated(new Date());
        sale1.setClientId(1);

        SaleEntity e = new SaleEntity();
        BeanUtils.copyProperties(sale1, e);
        List<Sale> sales = Arrays.asList(sale1);

        // Mock behaviors
        Mockito.doNothing().when(saleValidation).createSaleValidation(sales);
        Mockito.when(saleRepository.save(e)).thenReturn(e);

        createSaleService.create(sales);

        Mockito.verify(calcContext, Mockito.times(1)).calc(sale1.getAmount(), sale1.getProductValue(), CalcType.getByValue(sale1.getAmount()));
        Mockito.verify(saleRepository, Mockito.times(1)).save(e);
    }

}
