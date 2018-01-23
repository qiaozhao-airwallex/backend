package lemonstream.product;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lemonstream.BaseControllerIntegTest;
import lemonstream.image.ImageInfo;

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
        assertThat(createdProduct.getName(), is("name1"));
        assertThat(createdProduct.getDescription(), is("description1"));
        assertThat(createdProduct.getPrice(), is(new BigDecimal("11.23")));
        assertThat(createdProduct.getImageInfo(), notNullValue());
        assertThat(createdProduct.getImageInfo().getOriginalFileName(), is("originalFileName"));
        assertThat(createdProduct.getImageInfo().getTargetFileName(), is("targetFileName"));
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
        assertThat(listResponse.getBody().get(0).getName(), notNullValue());
        assertThat(listResponse.getBody().get(0).getName(), is("name1"));
        assertThat(listResponse.getBody().get(0).getDescription(), is("description1"));
        assertThat(listResponse.getBody().get(0).getPrice(), is(new BigDecimal("11.11")));
        assertThat(listResponse.getBody().get(0).getImageInfo(), notNullValue());
        assertThat(listResponse.getBody().get(0).getImageInfo().getOriginalFileName(), is("originalFileName"));
        assertThat(listResponse.getBody().get(0).getImageInfo().getTargetFileName(), is("targetFileName"));

        assertThat(listResponse.getBody().get(1), notNullValue());
        assertThat(listResponse.getBody().get(1).getName(), notNullValue());
        assertThat(listResponse.getBody().get(1).getName(), is("name2"));
        assertThat(listResponse.getBody().get(1).getDescription(), is("description2"));
        assertThat(listResponse.getBody().get(1).getPrice(), is(new BigDecimal("22.22")));
        assertThat(listResponse.getBody().get(1).getImageInfo(), notNullValue());
        assertThat(listResponse.getBody().get(1).getImageInfo().getOriginalFileName(), is("originalFileName2"));
        assertThat(listResponse.getBody().get(1).getImageInfo().getTargetFileName(), is("targetFileName2"));
    }

    private Product retrieveProduct(Long productId) {
        ResponseEntity<Product> infoResponse =
                template.getForEntity(baseURL.toString() + "product/" + productId, Product.class);
        return infoResponse.getBody();
    }

    private Long createProduct(String name, String description, BigDecimal price, ImageInfo imageInfo) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageInfo(imageInfo);
        ResponseEntity<Product> response =
                template.postForEntity(baseURL.toString() + "product", product, Product.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(response.getBody(), notNullValue());
        return response.getBody().getId();
    }

}