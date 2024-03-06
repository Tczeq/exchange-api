package pl.szlify.exchangeapi.controller;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szlify.exchangeapi.client.ExchangeClient;
import pl.szlify.exchangeapi.resource.ExchangeResource;
import pl.szlify.exchangeapi.model.TestModel;
import pl.szlify.exchangeapi.properties.ExchangeApiProperties;
import pl.szlify.exchangeapi.service.ExchangeService;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {
    private final ExchangeService exchangeService;
    private final ExchangeApiProperties exchangeApiProperties;

    @GetMapping("/test")
    public TestModel get() {
        return exchangeService.test();
    }

    @GetMapping("/test-response")
    public ResponseEntity<TestModel> getResponse() {
        TestModel testModel = exchangeService.test();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("name", "values")
                .body(testModel);
    }

    @GetMapping("/symbols")
    public ResponseEntity<ExchangeResource> getSymbols() {

        RequestInterceptor requestInterceptor = requestTemplate -> {
            requestTemplate.header("apikey", exchangeApiProperties.getApiKey());
        };

        ExchangeClient exchangeClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(ExchangeClient.class))
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(requestInterceptor)
                .target(ExchangeClient.class, exchangeApiProperties.getBaseUrl());

        ExchangeResource exchangeResource = exchangeClient.findAll();
        return new ResponseEntity<>(exchangeResource, HttpStatus.OK);
    }


}
