### 这是什么？

这是一个规范文档，用于规定各种规范类要求

---

### 接口类

- 参照[RESTFUL接口风格](https://blog.csdn.net/zzvar/article/details/118164133)，大概要求如下：
    - 路径需要使用名词，不能使用动词
    - 请求方式按照下表：
        - GET（SELECT）：从服务器取出资源（一项或多项）
        - POST（CREATE）：在服务器新建一个资源
        - PUT（UPDATE）：在服务器更新资源（更新完整资源）
        - PATCH（UPDATE）：在服务器更新资源， PATCH更新个别属性
        - DELETE（DELETE）：从服务器删除资源
    - 返回的状态码需要按照规范：
        - 200 OK - [GET]：服务器成功返回用户请求的数据
        - 201 CREATED - [POST/PUT/PATCH：用户新建或修改数据成功
        - 202 Accepted：表示一个请求已经进入后台排队（异步任务）
        - 204 NO CONTENT - [DELETE]：用户删除数据成功
        - 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的
        - 401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）
        - 403 Forbidden - [*]：表示用户得到授权（与401错误相对），但是访问是被禁止的
        - 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的
        - 405 Method Not Allowed：方法不允许，服务器没有该方法
        - 406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）
        - 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的
        - 422 Unprocessable entity - [POST/PUT/PATCH]:当创建一个对象时，发生一个验证错误
        - 500 INTERNAL SERVER ERROR - [*]:服务器发生错误，用户将无法判断发出的请求是否成功
    - 举个例子：
      - 在ArticleController里面，对article的增删改查操作
      - `get:/article/{id}`这代表获取对应id的article
      - `get:/article/list`这代表获取article列表
      - `post:/article`这代表提交一篇新的article
      - `put:/article`这代表修改整篇article
      - `patch:/article`这代表单独修改某个或某些属性
      - `delete:/article/{id}`删除某篇文章
- 权限规范：
  - 不需要登录就可以访问的接口需要使用`/public`作为前缀
  - 需要普通用户权限的接口直接使用`/user`作为前缀
  - 需要管理员权限的接口使用`/admin`作为前缀

---