package lemonstream.product;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemonstream.exception.AuthorizationFailureException;
import lemonstream.exception.EntityNotFoundException;
import lemonstream.image.ImageInfo;
import lemonstream.image.ImageRepository;
import lemonstream.user.User;
import lemonstream.util.MyBeanUtils;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public Product create(User user, Product product) {
        List<ImageInfo> imageList = new ArrayList<>();
        product.setOwner(user);
        product.getImageList().forEach(item -> {
            ImageInfo imageInfo = imageRepository.findById(item.getId());
            imageInfo.setDisplayOrder(item.getDisplayOrder());
            imageInfo.setProduct(product);
            imageList.add(imageInfo);
        });
        product.setImageList(imageList);
        return productRepository.save(product);
    }

    public Product findOne(Long id) throws EntityNotFoundException {
        Product product = productRepository.findOne(id);
        if (product == null) {
            throw new EntityNotFoundException(Product.class, "id", id.toString());
        }
        return product;
    }

    public Collection<Product> listPublished(User owner) {
        return productRepository.findByOwnerAndPublished(owner,true);
    }

    public Collection<Product> listUnPublished(User owner) {
        return productRepository.findByOwnerAndPublished(owner,false);
    }

    public void update(User user, Long id, Product updateRequest) {
        Product product = productRepository.findOne(id);
        if (!product.isOwnBy(user)) {
            throw new AuthorizationFailureException();
        }
        try {
            List<ImageInfo> imageList = new ArrayList<>();
            updateRequest.getImageList().forEach(item -> {
                ImageInfo imageInfo = imageRepository.findById(item.getId());
                imageInfo.setDisplayOrder(item.getDisplayOrder());
                imageInfo.setProduct(product);
                imageList.add(imageInfo);
            });
            updateRequest.setImageList(imageList);
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
