package pl.szlify.exchangeapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@ConfigurationProperties(prefix = "spring.task.execution.pool")
@Getter
@Setter
public class AsyncConfig {

    private int core_size;
    private int max_size;
    private int queue_capacity;

    @Bean
    public ThreadPoolTaskExecutor asyncTaskExecutor() {    //TODO: ThreadPoolTaskExecutor
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(core_size);
        executor.setMaxPoolSize(max_size);
        executor.setQueueCapacity(queue_capacity);
//        executor.initialize();
        return executor;
    }
}
