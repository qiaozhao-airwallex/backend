package lemonstream.product;


import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import lemonstream.image.ImageInfo;
import lemonstream.util.MyBeanUtils;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    public Product create(Product product) {
        product.getImageList().forEach(item -> {
            item.setProduct(product);
        });
        return productRepository.save(product);
    }

    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    public Collection<Product> listPublished() {
        return productRepository.findByPublished(true);
    }

    public Collection<Product> listUnPublished() {
        return productRepository.findByPublished(false);
    }

    public void update(Long id, Product updateRequest) {
        Product product = productRepository.findOne(id);
        try {
            MyBeanUtils.nullAndArrayAwareBeanCopy(product, updateRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        product.getImageList().forEach(item -> {
            if (item.getProduct() == null) {
                item.setProduct(product);
            }
        });
        productRepository.save(product);
    }
}
