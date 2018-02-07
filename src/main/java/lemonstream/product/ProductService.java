package lemonstream.product;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create(Product product) {
        product.getOtherImages().forEach(item -> {
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

}
