package lemonstream.product;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import lemonstream.util.RestPreconditions;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product createdProduct;
        try {
            createdProduct = productService.create(product);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Product> findOne(@PathVariable("id") Long id) {
        try {
            Product product = productService.findOne(id);
            RestPreconditions.checkFound(product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Product>> list(@RequestParam String category) {
        if ("published".equals(category)) {
            return new ResponseEntity<>(productService.listPublished(), HttpStatus.OK);
        } else if ("unPublished".equals(category)){
            return new ResponseEntity<>(productService.listUnPublished(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity update(@PathVariable Long id, HttpServletRequest request) throws IOException {
        Product product = productService.findOne(id);
        Product updatedProduct = objectMapper.readerForUpdating(product).readValue(request.getReader());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
