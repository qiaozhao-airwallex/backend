package lemonstream.image;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lemonstream.BaseControllerIntegTest;
import lemonstream.product.Product;

public class ImageControllerIntegTest extends BaseControllerIntegTest {

    @Test
    public void shouldCreateImage() throws Exception {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource("./test-image.webp"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<ImageInfo> response = template.exchange(
                baseURL.toString() + "image", HttpMethod.POST, requestEntity,
                ImageInfo.class);

        System.out.println(response.getBody());
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
//        ImageInfo storedImage = response.getBody();
//        String targetFileName = storedImage.getTargetFileName();
//        assertThat(storedImage.getTargetFileName(), notNullValue());
//
//        HttpEntity<LinkedMultiValueMap<String, Object>> getFileRequestEntity = new HttpEntity<>(headers);
//        ResponseEntity<Resource> getFileResponse = template.exchange(
//                baseURL.toString() + "image/" + targetFileName, HttpMethod.GET, getFileRequestEntity,
//                Resource.class);
//        assertThat(getFileResponse.getStatusCode(), equalTo(HttpStatus.OK));
//        assertThat(getFileResponse.getBody(), notNullValue());
    }
}