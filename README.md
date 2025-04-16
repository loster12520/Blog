需要在启动前增加一个配置文件`MyBlog-master/src/main/resources/application.yml`：

```yaml
server:
  #  port: 8090
  #本机端口
  port: 8888
spring:
    application:
        name: warrenblog

    datasource:
        url: jdbc:p6spy:mysql://localhost:3306/your_database_name
        username: root
        password: 123456
        driver-class-name: com.p6spy.engine.spy.P6SpyDriver

    data:
        redis:
            host: 127.0.0.1
            port: 6379
            database: 0
    security:
        salt: 'your_salt'
    servlet:
        multipart:
            max-request-size: 35MB  # 上传文件的总大小限制
            max-file-size: 10MB      # 单个文件的大小限制

mybatis-plus:
    configuration:
        #mp日志输出
        map-underscore-to-camel-case: true
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    global-config:
        db-config:
        id-type: ASSIGN_ID
        table-prefix: ms_

qiniu:
    accessKey: your_access_key
    accessSecretKey: your_access_secret_key
    bucketName: your_bucket_name
```