package pl.szlify.exchangeapi.service;

import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.SymbolsDto;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

public interface ExchangeService {

    SymbolsDto getAllSymbols();
    ConvertResponse getConvertedCurrency(String from, String to, BigDecimal amount);
}
