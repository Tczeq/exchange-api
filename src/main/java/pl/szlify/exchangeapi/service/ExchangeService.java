package pl.szlify.exchangeapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.client.ExchangeClient;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.SymbolsDto;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeClient exchangeClient;

    public SymbolsDto getAllSymbols() {
        return exchangeClient.findAll();
    }

    public ConvertResponse getConvertedCurrency(String from, String to, BigDecimal amount) {
        return exchangeClient.convert(from, to, amount);
    }
}
