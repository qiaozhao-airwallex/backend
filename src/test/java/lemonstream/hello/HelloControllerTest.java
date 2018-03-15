package lemonstream.hello;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lemonstream.product.Product;
import lemonstream.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Ignore
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }


    @Test
    public void test() throws IOException {
        Product p1 = new Product();
        p1.setId(1L);
        Product p2 = new Product();
        p2.setId(2L);

        User user = new User();
        user.setId(1L);
        user.setProductList(Arrays.asList(p1, p2));

        p1.setOwner(user);
        p2.setOwner(user);

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(p1);
        System.out.println(result);

        ObjectMapper mapper2 = new ObjectMapper();
        Object object = mapper2.readValue(result, Product.class);
        int a = 0;
    }

    @Test
    public void testB() {
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_12);
        FacebookClient.AccessToken accessToken = client.obtainAppAccessToken("164841907632025", "7bc7e44a64bc09dbf9113c66701ab4f7");

        FacebookClient client2 = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_12);

        FacebookClient.DebugTokenInfo debugTokenInfo = client2.debugToken(accessToken.getAccessToken());

        FacebookClient.DebugTokenInfo debugTokenInfo2 = client2.debugToken("EAACV7D9ZAN5kBAFRhn3b79VCMnAWxGFZC0Go5COIje4FlykF6LVJvvImegQSoHkgixOBxWpEnvsKRH6Mifacn4ZCft3LRvUiXgVfFs8ZCZCakwUIHoKMHId540SY01WVpkOk1knfmZA9JGA8zSTU0RFHvy4I1qVdqUgAkoMptJIeGvjocAbz8GtPAKXmo6ZCyV0vn9ghZAX1MQZDZD");
        int a = 0;
    }

}