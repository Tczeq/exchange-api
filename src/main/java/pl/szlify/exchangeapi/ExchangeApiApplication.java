package pl.szlify.exchangeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.szlify.exchangeapi.configuration.AsyncConfig;
import pl.szlify.exchangeapi.properties.ExchangeApiProperties;

@SpringBootApplication
@EnableConfigurationProperties({ExchangeApiProperties.class, AsyncConfig.class})
@EnableCaching
@EnableFeignClients
@EnableAsync
public class ExchangeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApiApplication.class, args);
    }

}
