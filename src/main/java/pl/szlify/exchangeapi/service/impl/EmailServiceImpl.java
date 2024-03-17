package pl.szlify.exchangeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.service.EmailService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String SUBJECT = "Exchange confirmation";

    private final JavaMailSender emailSender;
    private final String getEmailUsername; //Bean

    @Override
    public void sendConfirmation(String to, ConvertResponse convertResponse) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(getEmailUsername);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(createEmailContent(convertResponse)); //TODO: utworzyć metodę, która na bazie convertResponse utworzy treść maila
        emailSender.send(message);
    }

    @Override
    public void sendConf(String to) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(getEmailUsername);
//        message.setTo(to);
//        message.setSubject(SUBJECT);
//        message.setText("DUPA"); //TODO: utworzyć metodę, która na bazie convertResponse utworzy treść maila
//        emailSender.send(message);
    }

    public String createEmailContent(ConvertResponse convertResponse) {
        StringBuilder emailContent = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(convertResponse.getDate());
        emailContent.append("Potwierdzenie wymiany walut,")
                .append("\n")
                .append("z dnia: ")
                .append(date.format(formatter))
                .append("\n\n");
        emailContent.append("Sukces: ")
                .append(convertResponse.isSuccess() ? "Tak" : "Nie")
                .append("\n\n");

        DecimalFormat formatRate = new DecimalFormat("0.00");
        emailContent.append("Wymiana:")
                .append("\n");
        emailContent.append(" - ")
                .append(convertResponse.getQuery().getFrom())
                .append("/")
                .append(convertResponse.getQuery().getTo())
                .append("\n");
        emailContent.append(" - Ilość: ")
                .append(convertResponse.getQuery().getAmount())
                .append("\n");
        emailContent.append(" - Kurs: ")
                .append(formatRate.format(convertResponse.getInfo().getRate()))
                .append("\n");

        DecimalFormat format = new DecimalFormat("#.##");
        emailContent.append("Wynik transakcji: ")
                .append(format.format(convertResponse.getResult()))
                .append(" ")
                .append(convertResponse.getQuery().getTo());

        return emailContent.toString();
    }
}
