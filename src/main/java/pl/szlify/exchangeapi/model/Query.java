package pl.szlify.exchangeapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Query {
    private String from;
    private String to;
    private int amount;
}
