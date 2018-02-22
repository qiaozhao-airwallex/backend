package lemonstream.image;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lemonstream.exception.EntityNotFoundException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ImageInfo create(@RequestParam("file") MultipartFile file, Principal principal) throws EntityNotFoundException {
        return imageService.create(file);
    }


    @RequestMapping(value = "/{fileName:.+}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("fileName") String fileName, Principal principal) throws EntityNotFoundException {
            imageService.delete(fileName);
    }
}
