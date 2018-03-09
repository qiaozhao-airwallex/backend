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

import lemonstream.exception.AuthorizationFailureException;
import lemonstream.exception.EntityNotFoundException;
import lemonstream.exception.InvalidParameterValueException;
import lemonstream.user.User;
import lemonstream.user.UserService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

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
    public Collection<Product> listProducts(@RequestParam(defaultValue = "published") String category,
                                            @RequestParam(required = false) Long userId,
                                            Principal principal) throws EntityNotFoundException {
        User loginUser = (User) ((Authentication) principal).getPrincipal();
        User user;
        if (userId == null) {
            user = loginUser;
        } else {
            user = userService.findOne(userId);
        }

        if (!loginUser.equals(user) && !user.isFriendOf(loginUser)) {
            throw new AuthorizationFailureException("You can only see your own products or your friends published products");
        }
        if ("published".equals(category)) {
            return productService.listPublished(user);
        } else if ("unPublished".equals(category)) {
            if (!loginUser.equals(user)) {
                throw new AuthorizationFailureException("You can only see your friends' published products");
            }
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
