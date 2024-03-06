package pl.szlify.exchangeapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class ConvertResponse {

    private boolean success;
    private Query query;
    private Info info;
    private String date;
    private double result;
}
