package pl.szlify.exchangeapi.generator.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.szlify.exchangeapi.generator.PdfService;
import pl.szlify.exchangeapi.model.ConvertResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
@RequiredArgsConstructor
@Setter
@Getter

public class PdfGeneratorImpl implements PdfService {


    @Override
    public String createPdf(ConvertResponse convertResponse) {
        String path = System.getProperty("java.io.tmpdir") + "/confirmation_" + System.currentTimeMillis() + ".pdf";
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            addContentToDocument(document, convertResponse);
            addImageToDocument(document, "src/main/resources/ok.png");

        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            document.close();
        }

        return path;
    }

    private void addContentToDocument(Document document, ConvertResponse convertResponse) throws DocumentException {
        String emailContent = createEmailContent(convertResponse);
        String[] lines = emailContent.split("\n");
        for (String line : lines) {
            document.add(new Paragraph(line));
        }
    }

    private void addImageToDocument(Document document, String imagePath) throws IOException, DocumentException {
        Image image = Image.getInstance(imagePath);
        image.scalePercent(5);
        document.add(image);
    }


    @Override
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
