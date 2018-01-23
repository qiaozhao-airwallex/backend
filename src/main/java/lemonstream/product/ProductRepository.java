package lemonstream.product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private Map<Long, Product> products = new HashMap<>();
    private Long nextId = 0L;

    public synchronized Long generateNextId() {
        nextId++;
        return nextId;
    }

    public Product add(Product product) {
        Long id = generateNextId();
        product.setId(id);
        products.put(id, product);
        return product;
    }

    public Product findOne(Long id) {
        return products.get(id);
    }

    public Collection<Product> list() {
        return products.values();
    }

    public void deleteAll() {
        products.clear();
    }
}
