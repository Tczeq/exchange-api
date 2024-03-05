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
import pl.szlify.exchangeapi.model.ExchangeClient;
import pl.szlify.exchangeapi.model.ExchangeResource;
import pl.szlify.exchangeapi.model.ResponseApi;
import pl.szlify.exchangeapi.model.TestModel;
import pl.szlify.exchangeapi.properties.ExchangeApiProperties;
import pl.szlify.exchangeapi.service.ExchangeService;

import java.util.List;

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
                .header("My-Header", "My-Value")
                .body(testModel);
    }

//    @GetMapping("/symbols")
//    public ResponseEntity<List<ExchangeResource>> getResponseClient(){
//
//        ExchangeClient exchangeClient = Feign.builder()
//                .client(new OkHttpClient())
//                .encoder(new GsonEncoder())
//                .decoder(new GsonDecoder())
//                .logger(new Slf4jLogger(ExchangeClient.class))
//                .logLevel(Logger.Level.FULL)
//                .target(ExchangeClient.class, "http://localhost:8081/api/symbols");
//
//        List<ExchangeResource> symbols = exchangeClient.findAll();
//        return new ResponseEntity<>(symbols, HttpStatus.OK);
//    }

    @GetMapping("/symbols")
    public ResponseEntity<ResponseApi> getSymbols() {

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
                .target(ExchangeClient.class, "https://api.apilayer.com/exchangerates_data/symbols");



        ResponseApi responseApi = exchangeClient.findAll();
        return new ResponseEntity<>(responseApi, HttpStatus.OK);
    }


}
