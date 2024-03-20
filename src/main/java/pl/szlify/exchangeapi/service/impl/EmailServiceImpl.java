package pl.szlify.exchangeapi.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.generator.impl.PdfGeneratorImpl;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.service.EmailService;

import java.io.File;

@Service
@RequiredArgsConstructor
public
class EmailServiceImpl implements EmailService {

    private static final String SUBJECT = "Exchange confirmation";

    private final JavaMailSender emailSender;
    private final String getEmailUsername; //Bean
    private final PdfGeneratorImpl pdfGeneratorImpl;

    @Override
    public void sendConfirmation(String to, ConvertResponse convertResponse) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(getEmailUsername);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(pdfGeneratorImpl.createEmailContent(convertResponse));
        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(String to, ConvertResponse convertResponse) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(getEmailUsername);
            helper.setTo(to);
            helper.setSubject(SUBJECT);
            helper.setText(pdfGeneratorImpl.createEmailContent(convertResponse), false); //true dla html, false dla zwyklego tekstu

            FileSystemResource file = new FileSystemResource(new File(pdfGeneratorImpl.createPdf(convertResponse)));
            helper.addAttachment("Invoice.pdf", file, "application/pdf");

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
