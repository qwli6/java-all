### JQuery 事件

什么是事件？

页面对不同的访问者做出的响应就叫事件。

例如：

- 在元素上移动鼠标
- 选取单选按钮
- 点击元素

**常见的事件：**

| 鼠标事件   | 键盘事件 | 表单事件 | 文档/窗口事件 |
| ---------- | -------- | -------- | ------------- |
| click      | keypress | submit   | load          |
| dblclick   | keydown  | change   | resize        |
| mouseenter | keyup    | focus    | scroll        |
| mouseleave |          | blur     | upload        |

例如：

```javascript
$('p').click(function(){
    // 点击动作触发后执行的代码
});
```



#### 常用的 JQuery 事件方法

- 文档加载完成后执行

  ```javascript
  // 文档加载完成后执行该函数
  $(document).ready();
  ```

- 单击事件（click）

  ```javascript
  // 点击 p 元素隐藏该元素
  $('p').click(function(){
     $(this).hide(); 
  });
  ```

- 双击事件（dblclick）

  ```javascript
  // 双击 p 元素隐藏该元素
  $('p').dblclick(function(){
     	$(this).hide(); 
  });
  ```

  ```javascript
  $(document).ready(function(){
      $('#app').click(function(){
          $(this).hide();
      });
  
      $('#app1').dblclick(function(){
          $(this).hide();
      });
  });
  ```

  ```html
  <p id="app">单击我隐藏</p>
  <p id="app1">双击我隐藏</p>
  ```

- mouseenter 和 mouseleave

  ```javascript
   $('#image').mouseenter(function(){
       alert('鼠标进入了');
   });
  $('#image').mouseleave(function(){
      alert('鼠标离开了');
  });
  ```

- 鼠标（mousedown）按下或者抬起（mouseup）

  ```javascript
  $('#image').mousedown(function(){
      alert('鼠标按下了');
  });
  $('#image').mouseup(function(){
      alert('鼠标抬起了');
  });
  ```

- hover() 鼠标悬浮事件

  `hover()` 方法用于模拟光标悬停事件。

  当鼠标移动到元素上时，会触发指定的第一个函数（mouseenter），当鼠标移除元素的时候，会触发指定的第二个函数（mouseleave）。

  ```javascript
  $('#app').hover(function(){
      alert('鼠标进入了');
  }, function(){
      alert('鼠标移除了');
  });
  ```

- focus() 获取焦点事件

  当元素获取焦点的时候，会触发 `focus` 事件。

  ```javascript
  $('input').focus(function(){
      $(this).css('background-color', '#00ff00')
  });
  ```

- blur() 失去焦点事件

  当元素失去焦点时，会触发 `blur` 事件。

  ```javascript
  $('input').blur(function(){
      $(this).css('background-color', '#0000ff');
  });
  ```

> 注意键盘事件
>
> ```javascript
> $(window).keydown(function(event){
>     console.log('触发 keydown 事件');
>     // 可以获取到按下的键的 ASCII 码值
>     console.log(event.keyCode); 
> });
> ```

