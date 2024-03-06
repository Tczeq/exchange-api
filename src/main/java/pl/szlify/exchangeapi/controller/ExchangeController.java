package pl.szlify.exchangeapi.controller;

import feign.Param;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szlify.exchangeapi.model.ConvertRequest;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.SymbolsDto;
import pl.szlify.exchangeapi.service.ExchangeService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/symbols")
    public ResponseEntity<SymbolsDto> getSymbols() {
        SymbolsDto symbolsDto = exchangeService.getAllSymbols();
        return new ResponseEntity<>(symbolsDto, HttpStatus.OK);
    }

    @GetMapping("/convert")
    public ResponseEntity<ConvertResponse> convert(@Valid ConvertRequest convertRequest) {
        ConvertResponse response = exchangeService.getConvertedCurrency(convertRequest.getFrom(), convertRequest.getTo(), convertRequest.getAmount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/test")
//    public TestModel get() {
//        return exchangeService.test();
//    }
//
//    @GetMapping("/test-response")
//    public ResponseEntity<TestModel> getResponse() {
//        TestModel testModel = exchangeService.test();
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .header("name", "values")
//                .body(testModel);
//    }
}
