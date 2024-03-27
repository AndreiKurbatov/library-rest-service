package ua.com.foxmineded.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.foxmineded.library.models.BeanInitializationPlaceholder;

@Configuration
public class ApplicationConfig {
    @Bean
    public BeanInitializationPlaceholder beanInitializationPlaceholder() {
        return new BeanInitializationPlaceholder();
    }
}
