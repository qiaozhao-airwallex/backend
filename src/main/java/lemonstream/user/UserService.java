package lemonstream.user;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import com.restfb.FacebookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lemonstream.exception.EntityNotFoundException;
import lemonstream.exception.InvalidParameterValueException;
import lemonstream.exception.MissingRequiredParameterException;
import lemonstream.exception.UserAlreadyExistException;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FacebookClient facebookClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(username);
        String social = request.getParameter("social");
        if (social != null) {
            if ("facebook".equals(social)) {
                String accessToken = getRequiredField(request, "accessToken");
                String userId = getRequiredField(request, "userID");
                String name = getRequiredField(request, "name");
                try {
                    FacebookClient.DebugTokenInfo debugTokenInfo = facebookClient.debugToken(accessToken);
                    if (debugTokenInfo.isValid() && userId.equals(debugTokenInfo.getUserId())) {
                        String encodedPassword = passwordEncoder.encode(accessToken);
                        if (user == null) {
                            User newUser = new User();
                            newUser.setUsername(username);
                            newUser.setName(name);
                            newUser.setPassword(encodedPassword);
                            userRepository.save(newUser);
                            return newUser;
                        } else {
                            user.setPassword(encodedPassword);
                            return user;
                        }
                    } else {
                        throw new InsufficientAuthenticationException("Invalid token to verify a facebook user");
                    }
                } catch (Exception e) {
                    throw new AuthenticationServiceException("Failed to a verify facebook user" ,e);
                }
            }
            throw new InvalidParameterValueException("social", social);
        }
        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + "is not found");
        }
        return user;
    }

    private String getRequiredField(HttpServletRequest request, String fieldName) {
        String value = request.getParameter(fieldName);
        if (value == null) {
            throw new MissingRequiredParameterException(fieldName);
        }
        return value;
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User createdUser = userRepository.save(user);
            createdUser.setPassword(null);
            return createdUser;
        } catch (Exception e) {
            throw new UserAlreadyExistException("User:" + user.getUsername() + " already exists");
        }
    }

    public User findOne(Long id) throws EntityNotFoundException {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        return user;
    }

    public Collection<User> findAllFriends(Long id) throws EntityNotFoundException {
        return findOne(id).getFriends();
    }
}
