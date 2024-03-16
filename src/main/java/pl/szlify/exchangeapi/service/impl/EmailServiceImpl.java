package pl.szlify.exchangeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String SUBJECT = "Exchange confirmation";

    private final JavaMailSender emailSender;

    @Override public void sendConfirmation(String to, ConvertResponse convertResponse) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("exchangeapka@gmail.com");
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText("DUPA"); //TODO: utworzyć metodę, która na bazie convertResponse utworzy treść maila
        emailSender.send(message);
    }

    @Override
    public void sendConf(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("exchangeapka@gmail.com");
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText("DUPA"); //TODO: utworzyć metodę, która na bazie convertResponse utworzy treść maila
        emailSender.send(message);
    }

}
