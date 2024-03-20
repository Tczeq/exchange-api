package pl.szlify.exchangeapi.service;

import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import pl.szlify.exchangeapi.model.ConvertResponse;

import java.io.FileNotFoundException;

public interface EmailService {
    void sendConfirmation(String to, ConvertResponse convertResponse);
    void sendMessageWithAttachment(ConvertResponse convertResponse);
//    String createPdf(ConvertResponse convertResponse) throws FileNotFoundException, DocumentException;
}
