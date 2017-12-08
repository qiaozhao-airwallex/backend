package lemonstream.product;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lemonstream.BaseControllerIntegTest;

public class ProductControllerIntegTest extends BaseControllerIntegTest {

    @Test
    public void shouldCreateProduct() throws Exception {
        Product product = new Product();
        product.setName("name1");
        product.setDescription("description1");
        product.setPrice(new BigDecimal("11.23"));
        ResponseEntity<Long> response =
                template.postForEntity(baseURL.toString() + "product", product, Long.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(response.getBody(), notNullValue());

        ResponseEntity<Product> infoResponse =
                template.getForEntity(baseURL.toString() + "product/" + response.getBody(), Product.class);
        Product createdProduct = infoResponse.getBody();
        assertThat(createdProduct, notNullValue());
        assertThat(createdProduct.getName(), is("name1"));
        assertThat(createdProduct.getDescription(), is("description1"));
        assertThat(createdProduct.getPrice(), is(new BigDecimal("11.23")));
    }
}