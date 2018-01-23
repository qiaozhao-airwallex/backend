package lemonstream.product;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create(Product product) {
        return productRepository.add(product);
    }

    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    public Collection<Product> list() {
        return productRepository.list();
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }
}
