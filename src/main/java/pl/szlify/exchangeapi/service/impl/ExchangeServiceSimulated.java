package pl.szlify.exchangeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.client.ExchangeClient;
import pl.szlify.exchangeapi.model.ConvertRequest;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.Info;
import pl.szlify.exchangeapi.model.Query;
import pl.szlify.exchangeapi.model.SymbolsDto;
import pl.szlify.exchangeapi.properties.ExchangeApiProperties;
import pl.szlify.exchangeapi.service.EmailService;
import pl.szlify.exchangeapi.service.ExchangeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "exchange.api", name = "simulated", havingValue = "true")
public class ExchangeServiceSimulated implements ExchangeService {

    private final ExchangeApiProperties properties;
    private final EmailService emailService;
//    private final String getEmailUsername; //Bean


    @Override
    public SymbolsDto getAllSymbols() {
        return new SymbolsDto().setSymbols(Map.of("PLN", "", "USD", "", "EUR", ""));
    }

    @Override
    public ConvertResponse getConvertedCurrency(String from, String to, BigDecimal amount) {
        ConvertResponse convertResponse = ConvertResponse.builder()
                .success(true)
                .query(Query.builder()
                        .from("EUR")
                        .to("PLN")
                        .amount(300)
                        .build())
                .info(Info.builder()
                        .timestamp(123L)
                        .rate(4.30)
                        .build())
                .date(LocalDate.now().toString())
                .result(1290.26)
                .build();
        emailService.sendConfirmation(properties.getBaseUrl(), convertResponse);
        return convertResponse;
    }
}
