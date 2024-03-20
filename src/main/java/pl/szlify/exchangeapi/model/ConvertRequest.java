package pl.szlify.exchangeapi.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import pl.szlify.exchangeapi.validation.SupportedCurrency;

import java.math.BigDecimal;

@Data
@Builder
public class ConvertRequest {

    //todo: notNull?
    @SupportedCurrency
    private String from;

    //todo: notNull?
    @SupportedCurrency
    private String to;

    @NotNull
    @Positive
    private BigDecimal amount;
}
