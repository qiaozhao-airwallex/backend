package lemonstream.user;


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
import lemonstream.product.Product;
import lemonstream.product.ProductService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public User findOne(Principal principal) throws EntityNotFoundException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.findOne(user.getId());
    }

    @RequestMapping(value = "/me/friends/", method = RequestMethod.GET)
    @ResponseBody
    public Collection<User> listMyFriends(Principal principal) throws EntityNotFoundException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.findAllFriends(user.getId());
    }

}
