package lemonstream.product;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lemonstream.exception.EntityNotFoundException;
import lemonstream.exception.InvalidParameterException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Product findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        return productService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Collection<Product> list(@RequestParam String category) {
        if ("published".equals(category)) {
            Collection<Product> products = productService.listPublished();
            return products;
        } else if ("unPublished".equals(category)){
            return productService.listUnPublished();
        }
        throw new InvalidParameterException(category);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@PathVariable Long id, @RequestBody Product updateRequest) {
        productService.update(id, updateRequest);
    }
}
