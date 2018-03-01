package lemonstream.Configuration;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lemonstream.image.ImageService;
import lemonstream.image.PhysicalImageRepository;
import lemonstream.product.ProductService;

@Configuration
public class AppConfig {

    private String imageUploadedLocation = "uploaded";

    @Bean
    public PhysicalImageRepository physicalImageRepository() {
        return new PhysicalImageRepository(imageUploadedLocation);
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

    @Bean
    public FacebookClient facebookClient() {
        Version version = Version.VERSION_2_12;
        FacebookClient client = new DefaultFacebookClient(version);
        FacebookClient.AccessToken accessToken = client.obtainAppAccessToken("164841907632025", "7bc7e44a64bc09dbf9113c66701ab4f7");

        return new DefaultFacebookClient(accessToken.getAccessToken(), version);
    }
}
