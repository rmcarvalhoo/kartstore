package br.com.kartstore.service.strategy;

import br.com.kartstore.service.strategy.enums.CalcType;

import java.math.BigDecimal;

public interface CalcStrategy {
    BigDecimal calc(Long amount, BigDecimal productValue);
    CalcType notificationType();
}
