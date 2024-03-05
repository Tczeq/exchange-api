package pl.szlify.exchangeapi.model;

import feign.RequestLine;

import java.util.List;

public interface ExchangeClient {
    @RequestLine("GET")
    ResponseApi findAll();
}
