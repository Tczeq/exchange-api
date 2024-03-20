package pl.szlify.exchangeapi.generator.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
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
import java.text.MessageFormat;
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

            addTable(document, convertResponse);
            addContentToDocument(document, convertResponse);
            addImageToDocument(document);

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

    private void addImageToDocument(Document document) throws IOException, DocumentException {
        Image image = Image.getInstance("src/main/resources/ok.png");
        image.scalePercent(5);
        document.add(image);
    }


    @Override
    public String createEmailContent(ConvertResponse convertResponse) {
//        StringBuilder emailContent = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(convertResponse.getDate());
//        emailContent.append("Potwierdzenie wymiany walut,")
//                .append("\n")
//                .append("z dnia: ")
//                .append(date.format(formatter))
//                .append("\n\n");
//        emailContent.append("Sukces: ")
//                .append(convertResponse.isSuccess() ? "Tak" : "Nie")
//                .append("\n\n");
//
//        DecimalFormat formatRate = new DecimalFormat("0.00");
//        emailContent.append("Wymiana:")
//                .append("\n");
//        emailContent.append(" - ")
//                .append(convertResponse.getQuery().getFrom())
//                .append("/")
//                .append(convertResponse.getQuery().getTo())
//                .append("\n");
//        emailContent.append(" - Ilosc: ")
//                .append(convertResponse.getQuery().getAmount())
//                .append("\n");
//        emailContent.append(" - Kurs: ")
//                .append(formatRate.format(convertResponse.getInfo().getRate()))
//                .append("\n");
//
//        DecimalFormat format = new DecimalFormat("#.##");
//        emailContent.append("Wynik transakcji: ")
//                .append(format.format(convertResponse.getResult()))
//                .append(" ")
//                .append(convertResponse.getQuery().getTo());
        return MessageFormat.format("""
                Potwierdzenie wymiany walut,
                z dnia: {0}
                
                Sukces: {1}
                
                Wymiana: {2}/{3}
                Ilość: {4}
                """,
                date.format(formatter),
                convertResponse.isSuccess() ? "Tak" : "Nie",
                convertResponse.getQuery().getFrom(),
                convertResponse.getQuery().getTo(),
                convertResponse.getQuery().getAmount());

//        emailContent.append(" - Kurs: ")
//                .append(formatRate.format(convertResponse.getInfo().getRate()))
//                .append("\n");
//
//        DecimalFormat format = new DecimalFormat("#.##");
//        emailContent.append("Wynik transakcji: ")
//                .append(format.format(convertResponse.getResult()))
//                .append(" ")
//                .append(convertResponse.getQuery().getTo());

//        return emailContent.toString();
    }


    public void addTable(Document document, ConvertResponse convertResponse) {
        PdfPTable table = new PdfPTable(2);

        table.addCell("Data wymiany");
        table.addCell(convertResponse.getDate());
        table.addCell("Para walutowa");
        table.addCell(convertResponse.getQuery().getFrom() + "/" + convertResponse.getQuery().getTo());
        table.addCell("Ilosc");
        table.addCell(String.valueOf(convertResponse.getQuery().getAmount()));
        table.addCell("Kurs");
        table.addCell(String.valueOf(convertResponse.getInfo().getRate()));
        table.addCell("Wynik transakcji");
        table.addCell(String.valueOf(convertResponse.getResult()));

        try {
            document.add(table);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

}
