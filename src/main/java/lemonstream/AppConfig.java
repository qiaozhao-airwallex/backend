package lemonstream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lemonstream.image.ImageRepository;
import lemonstream.image.ImageService;
import lemonstream.product.ProductRepository;
import lemonstream.product.ProductService;

@Configuration
public class AppConfig {

    private String imageUploadedLocation = "uploaded";

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
        return new ImageService(imageUploadedLocation);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry
                        .addResourceHandler("/uploaded/**")
                        .addResourceLocations("file:./" + imageUploadedLocation + "/");
            }
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedMethods("*")
//                        .allowedHeaders("*");
//            }
        };
    }
}
