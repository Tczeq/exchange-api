package pl.szlify.exchangeapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import pl.szlify.exchangeapi.service.ExchangeService;

@RequiredArgsConstructor
public class SupportedCurrencyValidator implements ConstraintValidator<SupportedCurrency, String> {

    private final ExchangeService exchangeService;

    @Override
    public boolean isValid(String currencySymbol, ConstraintValidatorContext constraintValidatorContext) {
        return exchangeService.getAllSymbols().getSymbols().containsKey(currencySymbol);
    }
}
