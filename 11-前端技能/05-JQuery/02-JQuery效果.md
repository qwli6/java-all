### JQuery 效果

`JQuery` 效果包含隐藏，显示，切换，滑动，淡入淡出以及动画。

#### JQuery 隐藏和显示

> hide() 和 show()

```javascript
// 文档加载完成后执行
$(function(){
    $('#show').click(function(){
        $('p').show();
    });
    $('#hide').click(function() {
        $('p').hide();
    });
});
```

```html
<p>这是一个段落</p>
<button id="show">显示</button>
<button id="hide">隐藏</button>
```

语法：

```javascript
// speed 表示隐藏/显示的时间，也就是说在几秒内显示/隐藏，callback 表示显示/隐藏后的回调方法
$(selector).hide(speed, callback);
$(selector).show(speed, callback);
```

例如：

```javascript
$(function(){
    $('#show').click(function(){
        $('p').show(2000, function(){
            console.log("显示啦");
        });
    });
    $('#hide').click(function() {
        $('p').hide(2000, function(){
            console.log('隐藏啦');
        });
    });
});
```

执行效果如下：

![](../../99-ImageHouse/jquery/1.gif)



**注意：你也可以使用 toggle 方法来控制显示或者隐藏**

```javascript
// 2000 表示显示或者隐藏需要的时间 function 表示执行完成显示或者隐藏后执行的代码。
$('#toggle').click(function(){
    $('p').toggle(2000, function(){
        console.log("显示/隐藏");
    });
});
```

格式：

```javascript
$(selector).toggle(speed, callback);
```



#### JQuery 淡入/淡出

通过 `JQuery` 可以实现淡入淡出的效果。

- fadeIn 淡入

  用于淡入已经隐藏的元素

  语法：

  ```javascript
  // speed 和 callback 同上
  // speed 可取值为 slow fast 或者毫秒值
  $(selector).fadeIn(speed, callback);
  ```

  ```javascript
  $('#fadeIn').click(function(){
      $('p').fadeIn();
      $('p').fadeIn('slow');
      $('p').fadeIn(3000);
  });
  ```

- fadeOut 淡出

  用于淡出已经显示的元素

  用法同 `fadeIn`类似

  ```javascript
  $('#fadeOut').click(function(){
      $('p').fadeOut();
      $('p').fadeOut('slow');
      $('p').fadeOut(3000);
  });
  ```

  

