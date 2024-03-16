package pl.szlify.exchangeapi.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${spring.cache.caffeine.spec}")
    private String caffeineSpec;

    @Value("${spring.cache.cache-names}")
    private String cacheNames;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);
        cacheManager.setCaffeine(Caffeine.from(caffeineSpec));
        return cacheManager;
    }

//    @Bean
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager("cacheSymbols");
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                .initialCapacity(200) // określa poczatkową pojemnosc cache
//                .expireAfterWrite(5, TimeUnit.MINUTES)
//                .maximumSize(10000)
//                .recordStats()); //wlacza rejestrowanie statystyk przy uzyciu cache
//        return cacheManager;
//    }


}
