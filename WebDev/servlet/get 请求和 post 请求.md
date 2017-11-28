
# 请求（request）
 组成部分：请求行，请求头，请求体
 
 请求行：请求信息的第一行
 
 
 请求方式：get 和 post
 
 get 会把参数放在 url 的后面，post 不会
 
 get 参数大小有限制，post 请求没有限制
 
 get 请求没有请求体，post 请求有请求体，请求参数放在请求体中


### 1. Get 请求



### 2. Post 请求

只有 `post` 请求才有请求体， `get` 请求参数拼接在地址栏中，`post` 请求参数放在请求体中。

`get` 请求参数

```
http://www.xx.com?username=zhangsan&password=student
```


----

# 响应（response）

组成部分：响应行，响应头，响应体。


响应行：响应信息的第一行

```
格式：协议/版本 状态码  状态码说明
HTTP/1.1 200 OK

```

状态码：

> 200 正常响应成功
>
> 302 重定向
>
> 304 读缓存
> 
> 404 资源没找到，请求路径错误
> 
> 500 服务器内部异常



响应头：从响应信息到的第二行到空行结束

```
格式： key/value(value) 可以是多个值
```

```
常见头：
Location：http://www.xx.com/index.jsp --跳转方向，和 302 一起使用
Server:apache tomcat  --服务器型号
Content-Encoding gzip  --数据压缩
Content-Length: 80 --数据长度
Content-Language: zh-cn  --语言环境


```

响应体： 页面上展示的内容













