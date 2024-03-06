package pl.szlify.exchangeapi.client;

import feign.RequestLine;
import pl.szlify.exchangeapi.resource.ExchangeResource;


//@FeignClient(name = "api-service", url = "${exchange.api.api-key}")
public interface ExchangeClient {
    @RequestLine("GET /symbols")
    ExchangeResource findAll();
}
