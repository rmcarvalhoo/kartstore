package br.com.kartstore.controller.sale;

import br.com.kartstore.controller.sale.dto.Sale;
import br.com.kartstore.service.CreateSaleService;
import br.com.kartstore.service.FindSaleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private CreateSaleService cService;

    @Autowired
    private FindSaleService fService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Sale>> create(@RequestBody @Valid List<Sale> sales) {
        log.info("Create SaleDTO [{}]", sales);
        cService.create(sales);
        log.info("Sale [{}] created with success ", sales);
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }


    @GetMapping(name= "/find")
    public ResponseEntity<Page<Sale>> find(@RequestParam(value = "saleId", required = false) Long saleId,
                                           @RequestParam(value = "createdStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdStart,
                                           @RequestParam(value = "createdEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdEnd,
                                           @PageableDefault(sort = {}, direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable) {
        Page<Sale> sales = fService.findAll(saleId, createdStart, createdEnd, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

}

