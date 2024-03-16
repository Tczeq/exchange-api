package pl.szlify.exchangeapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Bean
    public String getEmailUsername(){
        return emailUsername;
    }
}
