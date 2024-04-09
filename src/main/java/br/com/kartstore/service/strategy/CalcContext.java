package br.com.kartstore.service.strategy;

import br.com.kartstore.service.strategy.enums.CalcType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Component
@Slf4j
public class CalcContext {
    private final Map<CalcType, CalcStrategy> sendNotificationByType;
    public BigDecimal calc(Long amount, BigDecimal productValue, CalcType calcType) throws RuntimeException {
        CalcStrategy calcStrategy = sendNotificationByType.getOrDefault(calcType, null);
        if (Objects.isNull(calcStrategy)) {
            throw new RuntimeException("Notificação não foi encontrada. tipo: " + calcType);
        }
        return calcStrategy.calc(amount, productValue);
    }
}
