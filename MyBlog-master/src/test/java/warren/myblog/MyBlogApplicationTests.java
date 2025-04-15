package warren.myblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class MyBlogApplicationTests {
    @Value("${spring.security.salt}")
    private String salt;
    @Test
    void contextLoads() {
    }
    @Test
    void  test(){

        String newpassword="1234567";
        String password = DigestUtils.md5DigestAsHex(newpassword.getBytes());
        System.out.println(password);
    }
}
