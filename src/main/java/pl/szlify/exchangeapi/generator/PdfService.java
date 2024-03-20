package pl.szlify.exchangeapi.generator;

import pl.szlify.exchangeapi.model.ConvertResponse;

public interface PdfService {
    String createPdf(ConvertResponse convertResponse);
    String createEmailContent(ConvertResponse convertResponse);
}
