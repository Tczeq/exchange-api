package pl.szlify.exchangeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.Info;
import pl.szlify.exchangeapi.model.Query;
import pl.szlify.exchangeapi.model.SymbolsDto;
import pl.szlify.exchangeapi.service.EmailService;
import pl.szlify.exchangeapi.service.ExchangeService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
//@ConditionalOnProperty(value = "exchange.api.simulated", havingValue = "true")
@ConditionalOnProperty(prefix = "notification", name = "exchange.api.simulated", havingValue = "true")
public class ExchangeServiceSimulated implements ExchangeService {

    private final EmailService emailService;
    private final String getEmailUsername; //Bean

    @Override
    public SymbolsDto getAllSymbols() {
        return null;
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
                .date("2024-03-06")
                .result(1290.26)
                .build();
        emailService.sendConfirmation(getEmailUsername, convertResponse);
        return convertResponse;
    }
}
