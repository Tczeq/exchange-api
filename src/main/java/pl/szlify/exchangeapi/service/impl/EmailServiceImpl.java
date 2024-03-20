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
        message.setText(createEmailContent(convertResponse));
        emailSender.send(message);
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


    @Override
    public void sendMessageWithAttachment(String pdfFile) {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(getEmailUsername);
            helper.setTo(getEmailUsername);
            helper.setSubject(SUBJECT);
            helper.setText("Attachment confirmation");

            FileSystemResource file = new FileSystemResource(new File(pdfFile));
            helper.addAttachment("Invoice.pdf", file, "application/pdf");

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }




    public String createPdf(ConvertResponse convertResponse) throws FileNotFoundException, DocumentException {

        String path = System.getProperty("java.io.tmpdir") + "/confirmation_" + System.currentTimeMillis() + ".pdf";


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();


        String emailContent = createEmailContent(convertResponse);
        String[] lines = emailContent.split("\n");
        for (String line : lines) {
            document.add(new Paragraph(line));
        }

        String imagePath = "src/main/resources/ok.png";
        Image image = null;
        try {
            image = Image.getInstance(imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image.scalePercent(5);
        document.add(image);

        document.close();
        return path;
    }

}
