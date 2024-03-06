package pl.szlify.exchangeapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.client.ExchangeClient;
import pl.szlify.exchangeapi.model.SymbolsDto;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeClient exchangeClient;

    public SymbolsDto getAllSymbols() {
        return exchangeClient.findAll();
    }
}
