package br.com.kartstore.service.strategy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.kartstore.service.strategy.enums.CalcType;
import br.com.kartstore.service.strategy.impl.FivePorcentStrategy;
import br.com.kartstore.service.strategy.impl.TenPorcentStrategy;
import br.com.kartstore.service.strategy.impl.ZeroPorcentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class CalcContextTest {

    private CalcContext calcContext;

    @BeforeEach
    public void setUp() {
        Map<CalcType, CalcStrategy> strategies = new HashMap<>();
        strategies.put(CalcType.NOTHING, new ZeroPorcentStrategy());
        strategies.put(CalcType.FIVE, new FivePorcentStrategy());
        strategies.put(CalcType.TEN, new TenPorcentStrategy());
        calcContext = new CalcContext(strategies);
    }

    @Test
    public void testCalc_Normal() {
        // Mock data
        Long amount = 3L;
        BigDecimal productValue = BigDecimal.valueOf(100);
        CalcType calcType = CalcType.NOTHING;

        // Perform the test
        BigDecimal result = calcContext.calc(amount, productValue, calcType);

        // Verify the result
        assertEquals(BigDecimal.valueOf(300), result);
    }

    @Test
    public void testCalc_Discount() {
        // Mock data
        Long amount = 10L;
        BigDecimal productValue = BigDecimal.valueOf(10);
        CalcType calcType = CalcType.FIVE;

        // Perform the test
        BigDecimal result = calcContext.calc(amount, productValue, calcType);

        // Verify the result
        assertEquals(BigDecimal.valueOf(95.00).intValue(), result.intValue());
    }


    @Test
    public void testCalc_DiscountTen() {
        // Mock data
        Long amount = 11L;
        BigDecimal productValue = BigDecimal.valueOf(10);
        CalcType calcType = CalcType.TEN;

        // Perform the test
        BigDecimal result = calcContext.calc(amount, productValue, calcType);

        // Verify the result
        assertEquals(BigDecimal.valueOf(99.00).intValue(), result.intValue());
    }

    @Test
    public void testCalc_UnknownType() {
        // Mock data
        Long amount = 11L;
        BigDecimal productValue = BigDecimal.valueOf(100);
        CalcType calcType = null;

        // Perform the test and verify the exception
        assertThrows(RuntimeException.class, () -> calcContext.calc(amount, productValue, calcType));
    }

}
