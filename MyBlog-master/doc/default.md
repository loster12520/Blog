# OpenAPI definition


**简介**:OpenAPI definition


**HOST**:http://localhost:8888


**联系人**:


**Version**:v0


**接口路径**:/v3/api-docs/default


[TOC]






# 上传图片


## upload


**接口地址**:`/user/upload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|image||query|true|file||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 评论


## comment


**接口地址**:`/user/remark`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "articleId": 0,
  "content": "",
  "parent": 0,
  "toUserId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|commentParam|CommentParam|body|true|CommentParam|CommentParam|
|&emsp;&emsp;articleId|||false|integer(int64)||
|&emsp;&emsp;content|||false|string||
|&emsp;&emsp;parent|||false|integer(int64)||
|&emsp;&emsp;toUserId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 发布文章


## publish


**接口地址**:`/user/publish`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "body": {
    "content": "",
    "contentHtml": ""
  },
  "category": {
    "id": 0,
    "avatar": "",
    "categoryName": ""
  },
  "summary": "",
  "tags": [
    {
      "id": 0,
      "tagName": "",
      "createId": 0
    }
  ],
  "title": "",
  "searchKeyword": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|articleParam|ArticleParam|body|true|ArticleParam|ArticleParam|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;body|||false|ArticleBodyParam|ArticleBodyParam|
|&emsp;&emsp;&emsp;&emsp;content|||false|string||
|&emsp;&emsp;&emsp;&emsp;contentHtml|||false|string||
|&emsp;&emsp;category|||false|CategoryVo|CategoryVo|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer||
|&emsp;&emsp;&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;&emsp;&emsp;categoryName|||false|string||
|&emsp;&emsp;summary|||false|string||
|&emsp;&emsp;tags|||false|array|TagVo|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer||
|&emsp;&emsp;&emsp;&emsp;tagName|||false|string||
|&emsp;&emsp;&emsp;&emsp;createId|||false|integer||
|&emsp;&emsp;title|||false|string||
|&emsp;&emsp;searchKeyword|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 删除评论


## deleteComment


**接口地址**:`/user/deleteComment`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 删除文章


## deleteArticle


**接口地址**:`/user/deleteArticle`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
[]
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|integers|integer|body|true|array||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 新增标签


## addTag


**接口地址**:`/user/add`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "avatar": "",
  "tagName": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|tagDTO|TagDTO|body|true|TagDTO|TagDTO|
|&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;tagName|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 注册


## register


**接口地址**:`/pulic/register`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "account": "",
  "admin": 0,
  "avatar": "",
  "createDate": "",
  "deleted": 0,
  "email": "",
  "lastLogin": "",
  "mobilePhoneNumber": "",
  "nickname": "",
  "password": "",
  "salt": "",
  "status": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|sysUser|SysUser|body|true|SysUser|SysUser|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;account|||false|string||
|&emsp;&emsp;admin|||false|integer(int32)||
|&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;createDate|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|integer(int32)||
|&emsp;&emsp;email|||false|string||
|&emsp;&emsp;lastLogin|||false|string(date-time)||
|&emsp;&emsp;mobilePhoneNumber|||false|string||
|&emsp;&emsp;nickname|||false|string||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;salt|||false|string||
|&emsp;&emsp;status|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 登录


## login


**接口地址**:`/public/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "account": "",
  "password": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|loginParams|LoginParams|body|true|LoginParams|LoginParams|
|&emsp;&emsp;account|||false|string||
|&emsp;&emsp;password|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 搜索文章


## search


**接口地址**:`/public/article/search`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "body": {
    "content": "",
    "contentHtml": ""
  },
  "category": {
    "id": 0,
    "avatar": "",
    "categoryName": ""
  },
  "summary": "",
  "tags": [
    {
      "id": 0,
      "tagName": "",
      "createId": 0
    }
  ],
  "title": "",
  "searchKeyword": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|articleParam|ArticleParam|body|true|ArticleParam|ArticleParam|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;body|||false|ArticleBodyParam|ArticleBodyParam|
|&emsp;&emsp;&emsp;&emsp;content|||false|string||
|&emsp;&emsp;&emsp;&emsp;contentHtml|||false|string||
|&emsp;&emsp;category|||false|CategoryVo|CategoryVo|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer||
|&emsp;&emsp;&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;&emsp;&emsp;categoryName|||false|string||
|&emsp;&emsp;summary|||false|string||
|&emsp;&emsp;tags|||false|array|TagVo|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer||
|&emsp;&emsp;&emsp;&emsp;tagName|||false|string||
|&emsp;&emsp;&emsp;&emsp;createId|||false|integer||
|&emsp;&emsp;title|||false|string||
|&emsp;&emsp;searchKeyword|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 首页文章列表


## listArticle


**接口地址**:`/public/article/list`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "page": 0,
  "pagesize": 0,
  "categoryId": 0,
  "year": "",
  "month": "",
  "tagId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|pageParams|PageParams|body|true|PageParams|PageParams|
|&emsp;&emsp;page|||false|integer(int32)||
|&emsp;&emsp;pagesize|||false|integer(int32)||
|&emsp;&emsp;categoryId|||false|integer(int64)||
|&emsp;&emsp;year|||false|string||
|&emsp;&emsp;month|||false|string||
|&emsp;&emsp;tagId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 退出登录


## logout


**接口地址**:`/user/logout`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|Authorization||header|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 获取用户登录信息


## getCurrenUser


**接口地址**:`/sysuser/currentUser`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|Authorization||header|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 查询所有标签


## getAllTag


**接口地址**:`/public/tags/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 最热标签(前六条)


## getHots


**接口地址**:`/public/tags/hot`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 导航-查询所有标签的详细信息


## getAllTagDetails


**接口地址**:`/public/tags/detail`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 实现点击标签可以查询到所有的文章


## findAllDetailsById


**接口地址**:`/public/tags/detail/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 查询文章下的所有评论


## comments


**接口地址**:`/public/comment/listComments/{articleId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|articleId||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 获取所有文章分类


## getAllCategory


**接口地址**:`/public/category/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 导航栏的文章分类功能


## getAllCategoryDetails


**接口地址**:`/public/category/detail`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 点击导航栏后显示文章分类和该分类下文章


## findCategoryDetailsById


**接口地址**:`/public/category/detail/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 获取文章详情


## viewArticle


**接口地址**:`/public/article/view/{articleId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|articleId||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 最新文章


## newArticle


**接口地址**:`/public/article/new`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 最热文章


## hotArticle


**接口地址**:`/public/article/hot`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 文章归档


## listArchive


**接口地址**:`/public/article/archive`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```


# 删除标签


## removeTagById


**接口地址**:`/user/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"success": true,
	"code": 0,
	"msg": "",
	"data": {}
}
```