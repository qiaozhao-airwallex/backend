package lemonstream.product;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lemonstream.exception.EntityNotFoundException;
import lemonstream.exception.InvalidParameterValueException;
import lemonstream.user.User;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Product create(@RequestBody Product product, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return productService.create(user, product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Product findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        return productService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Collection<Product> list(@RequestParam String category, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        if ("published".equals(category)) {
            Collection<Product> products = productService.listPublished(user);
            return products;
        } else if ("unPublished".equals(category)){
            return productService.listUnPublished(user);
        }
        throw new InvalidParameterValueException("category", category);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@PathVariable Long id, @RequestBody Product updateRequest, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        productService.update(user, id, updateRequest);
    }
}
