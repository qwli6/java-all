### JQuery 入门

`JQuery` 是一个 `JavaScript` 函数库。



#### JQuery 语法

JQuery 语法是通过选取 `HTML` 元素，并对选取的元素执行某些操作。

基础语法：

```javascript
$(selector).action();
```

- 使用 `$` 定义 `JQuery`

- 使用 `selector` 来指定选取的元素，`selector` 可以是 `id` 选择器，`class` 选择器，或者 `HTML` 标签元素。

- `action()` 来表示对元素执行的操作。

  

  例如：

  ```javascript
  $(this).hide();//隐藏当前元素
  $('p').hide();// 隐藏全部的 p 元素
  $('#app').hide();// 隐藏所有有 id 为 app 的元素 id = test
  $('p.test').hide();// 隐藏所有 p 标签中带有类选择器为 test 的元素， class = test
  ```

#### 文档就绪事件

```javascript
$(document).ready(function(){
    //文档加载完毕后执行这里的代码
});
```

为了保证所有对 `dom` 元素进行的操作都是在文档已经加载完毕后执行的，以避免不必要的错误。

```javascript
$(function(){
    //简洁写法
});
```



#### JQuery 选择器

`JQuery` 选择器允许您对 `HTML` 元素组或者单个 `HTML` 元素进行操作。

- 元素选择器

  `JQuery` 元素选择器基于元素名称进行选择

  ```javascript
  $('p') //选取全部的 p 标签元素
  ```

  例如：隐藏全部的 `p` 标签

  ```javascript
  // 点击按钮隐藏全部的 p 标签
  $(document).ready(function(){
      $("#click").click(function(){
          $("p").hide();
      });
  });
  ```

  ```html
  <h1>这是一个标题</h1>
  <p>这是一个段落</p>
  <p>这是另外一个段落</p>
  <button id="click">点我隐藏 p 标签</button>
  ```

- id 选择器

  `JQuery` 通过元素的 `id` 来选取唯一的**一个**元素。

  ```javascript
  $('#app') //选取 id = 'app' 的元素
  ```

  例如：隐藏 `id = app` 的元素标签

  ```javascript
   $(document).ready(function(){
       $("#click").click(function(){
           $("p").hide();
       });
  
       $('#button2').click(function(){
           $('#app').hide();
       })
   })
  ```

  ```html
  <p id="app">我有 id 为 app</p>
  <button id="button2">点我隐藏 id = '#app' 标签</button>
  ```

- class 选择器

  `JQuery` 通过元素的 `class` 来选取**一组**元素

  ```javascript
  $('.demo') //选取带有 class = 'demo' 的一组元素
  ```

  例如：隐藏 `class = 'demo'` 的一组元素

  ```html
  <p class="demo">我有 class 为 demo / 我是一号</p>
  <p class="demo">我有 class 为 demo / 我是二号</p>
  <button id="button3">点我隐藏 class = 'demo' 标签</button>
  ```

  ```javascript
  $(document).ready(function(){
      $("#click").click(function(){
          $("p").hide();
      });
  
      $('#button2').click(function(){
          $('#app').hide();
      });
  
      $('#button3').click(function(){
          $('.demo').hide();
      });
  });
  ```

- 更多选择器

  ```javascript
  $("*") //选取所有元素
  $(this) // 选取当前元素
  $('p.demo') // 选取 <p class="demo"></p> 元素
  $('p:first') // 选取第一个 p 标签元素
  $('ul li:first') // 选取 ul 中的第一个 li 元素
  $('[href]') // 选取带有 href 属性的元素
  $(':button') // 选取所有<input typt='button'/> 和 <button/> 元素
  ```

  

