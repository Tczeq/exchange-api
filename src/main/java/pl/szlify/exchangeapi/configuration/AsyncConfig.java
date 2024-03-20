package pl.szlify.exchangeapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean
    public ExecutorService asyncTaskExecutor() {    //TODO: ThreadPoolTaskExecutor
        return Executors.newFixedThreadPool(2); //TODO: dane konfiguracyjne zaciągać z yml
    }
}
