package pl.szlify.exchangeapi.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.szlify.exchangeapi.properties.ExchangeApiProperties;

@Configuration
//@EnableFeignClients(basePackages = "pl.szlify.exchangeapi.client")

public class ExchangeClientConfiguration {

//    @Bean
//    public ExchangeClient exchangeClient(ExchangeApiProperties properties) {
//        return Feign.builder()
//                .client(new OkHttpClient())
//                .encoder(new GsonEncoder())
//                .decoder(new GsonDecoder())
//                .logger(new Slf4jLogger(ExchangeClient.class))
//                .logLevel(Logger.Level.FULL)
//                .requestInterceptor(requestTemplate ->
//                    requestTemplate.header("apikey", properties.getApiKey()))
//                .target(ExchangeClient.class, properties.getBaseUrl());
//    }

    @Bean
    public RequestInterceptor requestInterceptor(ExchangeApiProperties properties) {
        return requestTemplate -> requestTemplate.header("apikey", properties.getApiKey());

    }
}
