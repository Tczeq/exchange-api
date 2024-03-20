package pl.szlify.exchangeapi.service.impl;


import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.client.ExchangeClient;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.SymbolsDto;
import pl.szlify.exchangeapi.service.EmailService;
import pl.szlify.exchangeapi.service.ExchangeService;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(ExchangeServiceSimulated.class)
@Setter

public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeClient exchangeClient;
    private final EmailService emailService;
    private final String getEmailUsername; //wstrzykniety @Bean z klasy EmailConfig, nie trzeba uzywac tutaj @Value

    @Scheduled(fixedRate = 300000) //1 minuta = 60000
    @Cacheable(cacheNames = "cacheSymbols")
    @Override
    public SymbolsDto getAllSymbols() {
        return exchangeClient.findAll();
    }

    //    @Scheduled(fixedRate = 10000) //1 minuta = 60000
    public ConvertResponse getConvertedCurrency(String from, String to, BigDecimal amount) throws DocumentException, FileNotFoundException {
        ConvertResponse convertResponse = exchangeClient.convert(from, to, amount);
        emailService.sendConfirmation(getEmailUsername, convertResponse);
        emailService.sendMessageWithAttachment(emailService.createPdf(convertResponse));
        return convertResponse;
    }

}
