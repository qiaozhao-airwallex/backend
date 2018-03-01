package lemonstream.product;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lemonstream.user.User;

@Repository
public interface ProductRepository  extends CrudRepository<Product, Long> {

    Collection<Product> findByOwnerAndPublished(User owner, boolean published);
}
