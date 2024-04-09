package br.com.kartstore.service;

import br.com.kartstore.controller.sale.dto.Sale;
import br.com.kartstore.service.database.entity.ClientEntity;
import br.com.kartstore.service.database.entity.SaleEntity;
import br.com.kartstore.service.database.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FindSaleService {

    @Autowired
    private SaleRepository repository;

    public List<Sale> findByNumControlIn(List<String> controls) {
        List<SaleEntity> sales = repository.findByNumControlIn(controls);
        return sales
                .stream()
                .map(SaleEntity::mapSale)
                .collect(Collectors.toList());
    }

    public Page<Sale> findAll(Long saleId,
                              Date createdStart,
                              Date createdEnd,
                              Pageable pageable) {
        Page<SaleEntity> entitySales = repository.findSales(saleId, createdStart, createdEnd, pageable);
        return entitySales.map(e -> {
            Sale s = Sale.builder()
                    .id(e.getId())
                    .numControl(e.getNumControl())
                    .created(e.getCreated())
                    .productName(e.getProductName())
                    .productValue(e.getProductValue())
                    .amount(e.getAmount())
                    .clientId(e.getClientId())
                    .build();
            return s;
        });
    }


}
