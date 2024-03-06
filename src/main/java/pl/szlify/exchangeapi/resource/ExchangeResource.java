package pl.szlify.exchangeapi.resource;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExchangeResource {
    private boolean success;
    private Map<String, String> symbols;
}
