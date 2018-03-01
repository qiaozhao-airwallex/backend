package lemonstream.image;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lemonstream.user.User;

@Repository
public interface ImageRepository  extends CrudRepository<ImageInfo, Long> {

    ImageInfo findById(Long id);
}
