package lemonstream.product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends CrudRepository<Product, Long> {

    Collection<Product> findByPublished(boolean published);
//    private Map<Long, Product> published = new HashMap<>();
//    private Map<Long, Product> unPublished = new HashMap<>();
//    private Long nextId = 0L;
//
//    public synchronized Long generateNextId() {
//        nextId++;
//        return nextId;
//    }
//
//    public Product add(Product product) {
//        Long id = generateNextId();
//        product.setId(id);
//        if (product.isPublished()) {
//            published.put(id, product);
//        } else {
//            unPublished.put(id, product);
//        }
//        return product;
//    }
//
//    public Product findOne(Long id) {
//        Product one = published.get(id);
//        if (one != null) {
//            return one;
//        }
//        return unPublished.get(id);
//    }
//
//    public Collection<Product> listPublished() {
//        return published.values();
//    }
//
//    public Collection<Product> listUnPublished() {
//        return unPublished.values();
//    }
}
