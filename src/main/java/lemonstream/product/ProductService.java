package lemonstream.product;


import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lemonstream.exception.AuthorizationFailureException;
import lemonstream.exception.EntityNotFoundException;
import lemonstream.image.ImageInfo;
import lemonstream.user.User;
import lemonstream.util.MyBeanUtils;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    public Product create(Product product) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        product.setOwner(user);
        product.getImageList().forEach(item -> {
            item.setProduct(product);
        });
        return productRepository.save(product);
    }

    public Product findOne(Long id) throws EntityNotFoundException {
        Product product = productRepository.findOne(id);
        if (product == null) {
            throw new EntityNotFoundException(Product.class, "id", id.toString());
        }
        return product;
    }

    public Collection<Product> listPublished() {
        return productRepository.findByPublished(true);
    }

    public Collection<Product> listUnPublished() {
        return productRepository.findByPublished(false);
    }

    public void update(Long id, Product updateRequest) {
        Product product = productRepository.findOne(id);
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (!product.isOwnBy(user)) {
            throw new AuthorizationFailureException();
        }
        try {
            MyBeanUtils.nullAndArrayAwareBeanCopy(product, updateRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        product.getImageList().forEach(item -> {
            if (item.getProduct() == null) {
                item.setProduct(product);
            }
        });
        productRepository.save(product);
    }
}
