package lemonstream.product;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Long create(Product product) {
        return productRepository.add(product);
    }

    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }
}
