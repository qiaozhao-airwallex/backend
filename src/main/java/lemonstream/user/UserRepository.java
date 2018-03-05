package lemonstream.user;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByUsername(String username);

    Collection<User> findFriendsById(Long id);
}
