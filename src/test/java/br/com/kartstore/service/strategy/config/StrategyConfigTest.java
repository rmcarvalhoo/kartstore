package br.com.kartstore.service.strategy.config;
import br.com.kartstore.service.strategy.CalcStrategy;
import br.com.kartstore.service.strategy.enums.CalcType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategyConfigTest {

    @Test
    public void testSendNotificationByType() {

        CalcStrategy strategy1 = Mockito.mock(CalcStrategy.class);
        CalcStrategy strategy2 = Mockito.mock(CalcStrategy.class);

        Mockito.when(strategy1.notificationType()).thenReturn(CalcType.NOTHING);
        Mockito.when(strategy2.notificationType()).thenReturn(CalcType.FIVE);

        List<CalcStrategy> notificationStrategies = Arrays.asList(strategy1, strategy2);
        StrategyConfig strategyConfig = new StrategyConfig(notificationStrategies);
        Map<CalcType, CalcStrategy> result = strategyConfig.sendNotificationByType();

        assertEquals(2, result.size());
        assertEquals(strategy1, result.get(CalcType.NOTHING));
        assertEquals(strategy2, result.get(CalcType.FIVE));
    }
}
