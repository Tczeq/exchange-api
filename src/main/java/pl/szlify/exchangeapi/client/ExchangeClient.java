package pl.szlify.exchangeapi.client;

import feign.Param;
import feign.RequestLine;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.SymbolsDto;

import java.math.BigDecimal;


//@FeignClient(name = "api-service", url = "${exchange.api.api-key}")
public interface ExchangeClient {
    @RequestLine("GET /symbols")
    SymbolsDto findAll();

    @RequestLine("GET /convert?from={from}&to={to}&amount={amount}")
    ConvertResponse convert(@Param("from") String from, @Param("to") String to, @Param("amount")BigDecimal amount);

}
