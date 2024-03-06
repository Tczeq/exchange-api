package pl.szlify.exchangeapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SymbolsDto {
    private boolean success;
    private Map<String, String> symbols;
}
