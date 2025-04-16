package warren.myblog.utils;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 令牌工具类 (使用 username 生成 token)
 */
public class JWTUtils {

    private static final String jwtToken = "123456Warren98!@#$$"; // 签名密钥
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 小时

    /**
     * 生成 Token (使用 username)
     */
    public static String createToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username); // 使用用户名作为载荷数据

        return Jwts.builder()
                .setClaims(claims)  // 设置载荷
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签名算法
                .compact();
    }

    /**
     * 解析 Token
     */
    public static Map<String, Object> checkToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtToken) // 设置签名密钥
                    .parseClaimsJws(token)  // 解析 JWT
                    .getBody(); // 获取载荷
            return claims;
        } catch (ExpiredJwtException e) {
            System.err.println("Token 已过期：" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("不支持的 Token：" + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("无效的 Token：" + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("签名验证失败：" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("非法参数：" + e.getMessage());
        }
        return null;
    }
}
