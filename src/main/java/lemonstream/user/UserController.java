package lemonstream.user;


import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lemonstream.exception.AuthorizationFailureException;
import lemonstream.exception.EntityNotFoundException;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User list(Principal principal, @PathVariable Long id)  throws EntityNotFoundException {
        User user = (User) ((Authentication) principal).getPrincipal();
        if (user.getId() != id) {
            throw new AuthorizationFailureException();
        }
        return userService.findOne(id);
    }

}
