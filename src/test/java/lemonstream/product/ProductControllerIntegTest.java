package lemonstream.product;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lemonstream.BaseControllerIntegTest;
import lemonstream.image.ImageInfo;
import lemonstream.user.User;

@Ignore
public class ProductControllerIntegTest extends BaseControllerIntegTest {

    @Before
    public void cleanUp() {
        template.delete(baseURL.toString() + "product");
    }

    @Test
    public void startWebServer() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        Long productId1 = createProduct("name1", "description1", new BigDecimal("11.23"),
                new ImageInfo("originalFileName", "targetFileName"));

        Product createdProduct = retrieveProduct(productId1);
        assertThat(createdProduct, notNullValue());
        assertThat(createdProduct.getSubject(), is("name1"));
        assertThat(createdProduct.getDescription(), is("description1"));
    }

    @Test
    public void shouldListAllProduct() throws Exception {
        createProduct("name1", "description1", new BigDecimal("11.11"),
                new ImageInfo("originalFileName", "targetFileName"));
        createProduct("name2", "description2", new BigDecimal("22.22"),
                new ImageInfo("originalFileName2", "targetFileName2"));

        ResponseEntity<List<Product>> listResponse =
                template.exchange(baseURL.toString() + "product", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Product>>() {});

        assertThat(listResponse.getBody(), notNullValue());
        assertThat(listResponse.getBody().size(), is(2));
        assertThat(listResponse.getBody().get(0), notNullValue());
        assertThat(listResponse.getBody().get(0).getSubject(), notNullValue());
        assertThat(listResponse.getBody().get(0).getSubject(), is("name1"));
        assertThat(listResponse.getBody().get(0).getDescription(), is("description1"));

        assertThat(listResponse.getBody().get(1), notNullValue());
        assertThat(listResponse.getBody().get(1).getSubject(), notNullValue());
        assertThat(listResponse.getBody().get(1).getSubject(), is("name2"));
        assertThat(listResponse.getBody().get(1).getDescription(), is("description2"));
    }

    private Product retrieveProduct(Long productId) {
        ResponseEntity<Product> infoResponse =
                template.getForEntity(baseURL.toString() + "product/" + productId, Product.class);
        return infoResponse.getBody();
    }

    private Long createProduct(String name, String description, BigDecimal price, ImageInfo imageInfo) {
        Product product = new Product();
        product.setSubject(name);
        product.setDescription(description);
        ResponseEntity<Product> response =
                template.postForEntity(baseURL.toString() + "product", product, Product.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(response.getBody(), notNullValue());
        return response.getBody().getId();
    }

}