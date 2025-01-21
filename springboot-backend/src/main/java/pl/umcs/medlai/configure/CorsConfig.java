package pl.umcs.medlai.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Zezwala na wszystkie endpointy
                        .allowedOrigins("http://localhost:5173", "http://localhost:5174") // Adres frontendu
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Dozwolone metody HTTP
                        .allowedHeaders("*") // Dozwolone nagłówki
                        .allowCredentials(true); // Zezwolenie na ciasteczka (opcjonalnie)
            }
        };
    }
}
