package br.com.kartstore.service.strategy.impl;

import br.com.kartstore.service.strategy.CalcStrategy;
import br.com.kartstore.service.strategy.enums.CalcType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class TenPorcentStrategy implements CalcStrategy {

    @Override
    public BigDecimal calc(Long amount, BigDecimal productValue) {
        final BigDecimal total = productValue.multiply(BigDecimal.valueOf(amount.longValue()));
        BigDecimal discount = total.multiply(BigDecimal.valueOf(CalcType.TEN.getCod().longValue()).divide(BigDecimal.valueOf(100)));
        BigDecimal totalDiscount = total.subtract(discount);
        log.info("Total discount {} ", totalDiscount);
        return totalDiscount;
    }

    @Override
    public CalcType notificationType() {
        return CalcType.TEN;
    }

}
