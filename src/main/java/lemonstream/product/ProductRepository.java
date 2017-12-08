package lemonstream.product;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private Map<Long, Product> products = new HashMap<>();
    private Long nextId = 0L;

    public synchronized Long generateNextId() {
        nextId++;
        return nextId;
    }

    public Long add(Product product) {
        Long id = generateNextId();
        products.put(id, product);
        return id;
    }

    public Product findOne(Long id) {
        return products.get(id);
    }
}
