#### JQuery  $.get() 方法

`$.get()` 方法通过 `http GET` 从服务器上请求数据。

语法：

```javascript
// 必选的 url 参数表示希望请求的 url
// 可选的 callback 参数是请求成功后执行的回调函数名称
$.get(url, callback);
```

```javascript
$(document).ready(function(){
    $('#get').click(function(){
        $.get('test.php',function(data, status){
            alert(status);
        });
    });
});
```



#### JQuery $.post() 方法

`$.post()` 通过 `http post` 请求向服务器提交数据。

语法：

```javascript
// 必选的 url 参数表示希望请求的 url
// 可选的 data 参数规定连同请求发送的数据
// callback 表示请求成功后的回调方法
$.post(url, data, callback);
```

```javascript
$('#post').click(function(){
    $.post('test.php',{
        name: 'zhang',
        password: 'student'
    }, function(data, status){
        alert(status);
    });
});
```