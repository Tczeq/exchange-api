package pl.szlify.exchangeapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szlify.exchangeapi.model.ConvertRequest;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.SymbolsDto;
import pl.szlify.exchangeapi.service.ExchangeService;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
@Slf4j
public class ExchangeController {

    private final ExchangeService exchangeService;
//    @Cacheable(cacheNames = "cacheSymbols") //TODO: do serwisu
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


}
