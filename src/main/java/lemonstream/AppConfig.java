package lemonstream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lemonstream.image.ImageRepository;
import lemonstream.image.ImageService;
import lemonstream.product.ProductRepository;
import lemonstream.product.ProductService;

@Configuration
public class AppConfig {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository();
    }

    @Bean
    public ProductService productService() {
        return new ProductService();
    }

    @Bean
    public ImageRepository imageRepository() {
        return new ImageRepository();
    }

    @Bean
    public ImageService imageService() {
        return new ImageService("uploaded");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
