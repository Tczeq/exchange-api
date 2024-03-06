package pl.szlify.exchangeapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Info {
    private long timestamp;
    private double rate;

}
