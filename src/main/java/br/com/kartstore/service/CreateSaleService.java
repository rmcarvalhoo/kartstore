package br.com.kartstore.service;

import br.com.kartstore.controller.sale.dto.Sale;
import br.com.kartstore.service.database.entity.SaleEntity;
import br.com.kartstore.service.database.repository.SaleRepository;
import br.com.kartstore.service.strategy.CalcContext;
import br.com.kartstore.service.strategy.enums.CalcType;
import br.com.kartstore.service.validation.SaleValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class CreateSaleService {

    @Autowired
    private SaleRepository repository;

    @Autowired
    private SaleValidation saleValidation;

    @Autowired
    private CalcContext calcContext;

    public void create(List<Sale> sales) {
        saleValidation.createSaleValidation(sales);
        save(sales);
    }

    private void save(List<Sale> sales) {
        log.debug("Creating the sale [{}]", sales);
        sales.stream().forEach(s -> {
            SaleEntity e = new SaleEntity();
            BeanUtils.copyProperties(s, e);
            e.setOrderTotal(calcContext.calc(s.getAmount(), s.getProductValue(), CalcType.getByValue(s.getAmount())));
            e = repository.save(e);
            s.setId(e.getId());
            log.debug("The sale [{}] created with success", e);
        });

    }

}
