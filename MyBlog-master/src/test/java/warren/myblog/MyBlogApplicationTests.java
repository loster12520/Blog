package warren.myblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

@SpringBootTest
class MyBlogApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
    }
    @Test
    void  test(){
        String newpassword="123456";
        String encode = passwordEncoder.encode(newpassword);
        System.out.println(encode);
    }
}
