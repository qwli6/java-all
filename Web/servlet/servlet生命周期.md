# Servlet 生命周期

### 1.init() 方法

> 第一次访问的时候被调用，且只会执行一次

```
//第一次访问的时候被调用
@Override
public void init(ServletConfig config) throws ServletException {
    super.init(config);
    System.out.println("初始化....init()");
}
```


### 2.destory() 方法

> `servlet` 被移除或者服务器正常关闭的时候调用


```
//销毁
@Override
public void destroy() {
    super.destroy();
    System.out.println("销毁....destroy()");

}
```



### 3. service() 方法

> 请求一次执行一次

```
//服务，处理业务逻辑
@Override
public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    super.service(req, res);
    System.out.println("服务....service()");

}
```


---


**`servlet` 是单实例多线程，默认第一次访问的时候，服务器创建 `servlet`，并且调用`init` 实现初始化操作，并调用一次 `service` 方法，每当请求来的时候，服务器创建一个线程，调用`service`方法执行自己的业务逻辑，当`servlet` 被移除的时候或者服务器正常关闭的时候，服务器调用`servlet`的`destory`方法实现销毁操作。**




-----

# 定时跳转

实现 `3`秒跳转到 `index.jsp`页面

```
//3秒跳转到index.jsp
resp.setHeader("refresh", "3;url=/day08_servlet/index.jsp");
```















