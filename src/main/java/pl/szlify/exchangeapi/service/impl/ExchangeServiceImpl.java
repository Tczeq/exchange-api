package pl.szlify.exchangeapi.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.client.ExchangeClient;
import pl.szlify.exchangeapi.generator.impl.PdfGeneratorImpl;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.SymbolsDto;
import pl.szlify.exchangeapi.service.EmailService;
import pl.szlify.exchangeapi.service.ExchangeService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(ExchangeServiceSimulated.class)
@Setter
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeClient exchangeClient;
    private final EmailService emailService;

//    @Scheduled(fixedRate = 300000) //1 minuta = 60000
    @Cacheable(cacheNames = "cacheSymbols")
    @Override
    public SymbolsDto getAllSymbols() {
        return exchangeClient.findAll();
    }

    @Override
    public ConvertResponse getConvertedCurrency(String from, String to, BigDecimal amount) {
        ConvertResponse convertResponse = exchangeClient.convert(from, to, amount);
//        emailService.sendConfirmation(getEmailUsername, convertResponse);
        emailService.sendMessageWithAttachment("exchangeapka@gmail.com", convertResponse);
        return convertResponse;
    }

}
