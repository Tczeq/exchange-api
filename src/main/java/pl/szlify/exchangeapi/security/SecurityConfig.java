package pl.szlify.exchangeapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults());
//        return http.build();
//    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john")
                .password("test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(john, mary, susan);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/exchange/symbols").hasRole("EMPLOYEE")
//                        .requestMatchers(HttpMethod.GET, "/api/exchange/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/exchange/convert/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/exchange/convert/**").hasRole("ADMIN")

        );
        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

}
