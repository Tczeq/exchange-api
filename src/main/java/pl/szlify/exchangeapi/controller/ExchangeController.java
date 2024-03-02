package pl.szlify.exchangeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szlify.exchangeapi.model.TestModel;
import pl.szlify.exchangeapi.service.ExchangeService;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {
    private final ExchangeService exchangeService;

    @GetMapping("/test")
    public TestModel get() {
        return exchangeService.test();
    }

    @GetMapping("/test")
    public ResponseEntity<TestModel> getResponse(){
        TestModel testModel = exchangeService.test();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("My-Header", "My-Value")
                .body(testModel);
    }
}
