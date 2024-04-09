package br.com.kartstore.service.strategy.config;

import br.com.kartstore.service.strategy.enums.CalcType;
import br.com.kartstore.service.strategy.CalcStrategy;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class StrategyConfig {
    private final List<CalcStrategy> notificationStrategies;

    @Bean
    public Map<CalcType, CalcStrategy> sendNotificationByType() {
        Map<CalcType, CalcStrategy> messagesByType = new EnumMap<CalcType, CalcStrategy>(CalcType.class);
        notificationStrategies.forEach(notificationStrategy -> messagesByType.put(notificationStrategy.notificationType(), notificationStrategy));
        return messagesByType;
    }
}
