package lemonstream.image;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lemonstream.exception.EntityNotFoundException;
import lemonstream.user.User;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ImageInfo create(@RequestParam("file") MultipartFile file, Principal principal) throws EntityNotFoundException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return imageService.create(user, file);
    }


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id, Principal principal) throws EntityNotFoundException {
        User user = (User) ((Authentication) principal).getPrincipal();
        imageService.delete(user, id);
    }
}
