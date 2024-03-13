package pl.szlify.exchangeapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ConvertResponse {

    private boolean success;
    private Query query;
    private Info info;
    private String date;
    private double result;
}
