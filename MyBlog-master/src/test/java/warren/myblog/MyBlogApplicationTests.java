package warren.myblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MyBlogApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
    }
    @Test
    void  test(){
        String rawPassword="123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        // 打印加密后的密码（每次运行都会不同，因为 BCrypt 使用随机盐值）
        System.out.println("加密后的密码: " + encodedPassword);

        // 验证加密后的密码是否与明文密码匹配
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));

        // 验证错误的密码是否不匹配
        assertFalse(passwordEncoder.matches("wrongPassword", encodedPassword));
    }
}
