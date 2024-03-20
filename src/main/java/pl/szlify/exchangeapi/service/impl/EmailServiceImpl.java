package pl.szlify.exchangeapi.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.szlify.exchangeapi.generator.PdfGenerator;
import pl.szlify.exchangeapi.model.ConvertResponse;
import pl.szlify.exchangeapi.service.EmailService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public
class EmailServiceImpl implements EmailService {

    private static final String SUBJECT = "Exchange confirmation";

    private final JavaMailSender emailSender;
    private final String getEmailUsername; //Bean

    private final PdfGenerator pdfGenerator;

    @Override
    public void sendConfirmation(String to, ConvertResponse convertResponse) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(getEmailUsername);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(pdfGenerator.createEmailContent(convertResponse));
        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(ConvertResponse convertResponse) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(getEmailUsername);
            helper.setTo(getEmailUsername);
            helper.setSubject(SUBJECT);
            helper.setText(pdfGenerator.createEmailContent(convertResponse), false); //true dla html, false dla zwyklego tekstu

            FileSystemResource file = new FileSystemResource(new File(pdfGenerator.createPdf(convertResponse)));
            helper.addAttachment("Invoice.pdf", file, "application/pdf");

            emailSender.send(message);
        } catch (MessagingException | FileNotFoundException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }

}
