package pl.szlify.exchangeapi.service;

import pl.szlify.exchangeapi.model.ConvertResponse;

public interface EmailService {
    void sendConfirmation(String to, ConvertResponse convertResponse);
}
