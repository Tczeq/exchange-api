package pl.szlify.exchangeapi.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.client.ExchangeClient;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.Info;
import pl.szlify.exchangeapi.model.Query;
import pl.szlify.exchangeapi.model.SymbolsDto;
import pl.szlify.exchangeapi.service.EmailService;
import pl.szlify.exchangeapi.service.ExchangeService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(ExchangeServiceSimulated.class)
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeClient exchangeClient;
    private final EmailService emailService;

    @Override public SymbolsDto getAllSymbols() {
        return exchangeClient.findAll();
    }

    public ConvertResponse getConvertedCurrency(String from, String to, BigDecimal amount) {
        ConvertResponse convertResponse = exchangeClient.convert(from, to, amount);
        emailService.sendConfirmation("testMail@gmail.com", convertResponse); //TODO: mail do poprawienia
        return convertResponse;
    }
}
