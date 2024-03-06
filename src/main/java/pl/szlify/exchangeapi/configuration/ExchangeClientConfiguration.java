package pl.szlify.exchangeapi.configuration;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.szlify.exchangeapi.client.ExchangeClient;
import pl.szlify.exchangeapi.properties.ExchangeApiProperties;

@Configuration
public class ExchangeClientConfiguration {

    @Bean
    public ExchangeClient exchangeClient(ExchangeApiProperties properties) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(ExchangeClient.class))
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(requestTemplate ->
                    requestTemplate.header("apikey", properties.getApiKey()))
                .target(ExchangeClient.class, properties.getBaseUrl());
    }
}
