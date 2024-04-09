package br.com.kartstore.service.strategy.impl;

import br.com.kartstore.service.strategy.CalcStrategy;
import br.com.kartstore.service.strategy.enums.CalcType;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ZeroPorcentStrategy implements CalcStrategy {

    @Override
    public BigDecimal calc(Long amount, BigDecimal productValue) {
        final BigDecimal total = productValue.multiply(BigDecimal.valueOf(amount.longValue()));
        log.info("Total with discount {} ", total);
        return total;
    }

    @Override
    public CalcType notificationType() {
        return CalcType.NOTHING;
    }

}
