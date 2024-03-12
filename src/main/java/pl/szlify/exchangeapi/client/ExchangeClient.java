package pl.szlify.exchangeapi.client;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.model.SymbolsDto;

import java.math.BigDecimal;


@FeignClient(name = "api-service", url = "${exchange.api.api-key}")
public interface ExchangeClient {


    //stare
//    @RequestLine("GET /symbols")
//    SymbolsDto findAll();
//
//    @RequestLine("GET /convert?from={from}&to={to}&amount={amount}")
//    ConvertResponse convert(@Param("from") String from, @Param("to") String to, @Param("amount")BigDecimal amount);

    //nowe

    @GetMapping("/symbols")
    SymbolsDto findAll();

    @GetMapping("/convert")
    ConvertResponse convert(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("amount") BigDecimal amount);
}
