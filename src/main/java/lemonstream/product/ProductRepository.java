package lemonstream.product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends CrudRepository<Product, Long> {

    Collection<Product> findByPublished(boolean published);
}
