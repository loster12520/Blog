### 导库

导入`org.springframework.boot:spring-boot-starter-security`这个依赖就可以了，SpringBoot有统一管理版本，所以不需要额外指定版本w

---

### 用户认证

存储用户，首先需要定义好用户的数据表结构，需要有账号名和密码

需要先定义好一个`userRepository`，可以直接使用`getUserByUserName`这个函数通过用户名获取密码

下一步我们需要自定义一个用户模型，用来保存用户账号密码以及权限组

```java
public class CustomUser {
    private CustomUserModel customUserModel;    // 你自己定义的用户pojo
    private Collection<GrantedAuthority> grantedAuthorities;    // 权限组信息
    //  构造函数和getter以及setter都省略
}
```

接下来是定义一个类来继承SpringSecurity里面的`UserDetailsService`接口，并设置成Bean供SpringSecurity使用

```java
@Component
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;  // 你获取保存在数据库里面用户信息的仓库
    @Override
    public UserDetails loadUserByUsername(String username){ //一定要实现的方法w
        if(username == null){   //  判断是否为空
            throw new UsernameNotFoundException("username is empty");
        }
        CustomUserModel user = userRepository.getUserByUserName(username);
        return new CustomUser(user, ArrayList());   // 返回一个用户模型
    }
}
```

然后在`SecurityConfig`里面增加一个认证管理器的Bean，直接使用库里面的就行

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
    return authenticationConfiguration.authenticationManager;
}
```

接下来需要写一个`LoginService`，用来实现登录的逻辑

```java
public class LoginService {
    // 自己导入一下redisCache、authenticationManager、jwtUtils的几个自动注入（实在不想在这里愚蠢的写了）
    public Map<String, String> login(String userName, String password) {
        Authentication authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userDTO.userName, userDTO.password));
        authenticate.getPrincipal(); // 用这个获取一下目前认证的CustomUser，函数返回的是Object，需要强转成CustomUser然后再做下一步操作
        //！！下面我就不写了，只说逻辑你按着你的想法实现
        // 使用user的userName去从jwtUtils生成一个token，待会返回给controller
        // 把userId给保存在Redis里面，方便待会读取
        // 直接返回一个map，里面放一个 "token"->token   （当然你也可以直接返回一串token信息，或者是使用一个vo给包装起来也可以，随便怎么写只要能把token返回给前端就没问题）
    }
}
```

最后就是写一个`LoginController`，用来提高接口给前端调用

--- 

### 用户认证

用户认证需要实现一个过滤链，然后检测标头里面是否包含着token，如果有正确的token的话那就放行

所以我们需要自己编写一条过过滤链`JwtAuthenticationTokenFilter`：

```java
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ){
        String token = request.getHeader("token");
        // 如果为空就直接放行
        if (token.isNullOrBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        // jwt解码成userId（或许应该叫做UserName）
        String userId = jwtUtils.parseJWT(token).subject;
        // 检查Redis里面有没有保存这个数据
        Object loginUser = redisCache.getCacheObject("login:$userId");
        // 如果为空就报错w
        if (loginUser == null)
            throw new RuntimeException("user not logged in");
        // 把id保存进SecurityContextHolder里面
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
```

--- 

### 加密

咱们现在并没有进行加密操作，所以现在需要做一下

创建一个`CustomPasswordEncoder`类，继承自`PasswordEncoder`

```java
@Component
public class CustomPasswordEncoder extends PasswordEncoder{
    @Override
    String encode(CharSequence rawPassword){
        return String(DigestUtils.md5Digest(rawPassword.toString().byteInputStream()));
    }
    @Override
    Boolean matches(CharSequence rawPassword,String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
```

---

### 注册

和普通接口写法差不多，直接往userRepository增加一个新的用户就行

也是直接post进接口，然后save一下就行了w

---

### 过滤器链和跨域设置

居然忘记写这一段了，再后面补一补吧

刚刚认证的时候需要用到的过滤器，我们需要设置好才能正常使用

在`SecurityConfig`里面增加一个bean：

```java
import java.util.List;@Bean
public SecurityFilterChain filterChain(
        HttpSecurity http,
        CorsConfigurationSource  corsConfigurationSource,
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter
    ){
    return http                         ;
//            .csrf { it.disable() }
//            .cors(Customizer.withDefaults())
//            .cors { it.configurationSource(corsConfigurationSource) }
//            .authorizeHttpRequests {
//        it.requestMatchers("/public/**").permitAll()
//                .anyRequest().authenticated()
//    }
//            .sessionManagement {
//        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//    }.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.getClass())
//            .build()
    // 大概就是这么一大串，这个是kotlin代码，我懒得一段一段翻译了w
    // 你可以找一找网上的文章复制一下
    // 或者按着函数名然后搜一下怎么使用，或者吧函数后面带花括号这个变成匿名类或者lambda写一下代码呗，都差不多这样
    // 功能就是要求所有public的都放开，其他的都需要认证，csrf禁用，cors用待会自己配置的跨域
    // 然后就是配置一下sessionManagement，设置成STATELESS，保证SecurityContext的配置咱们手动来不要依赖框架
    // 最后再配置一下过滤器，把jwtAuthenticationTokenFilter放在UsernamePasswordAuthenticationFilter前面
}
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```
---

ok，到现在就完成了所有的SpringSecurity配置

进一步的配置方向，可以多增加几个role，用来规定user或者admin，就只需要在多写点东西（我还没有研究这块，之后咱们需要再增加吧w）