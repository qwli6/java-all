## HTML 中的 div 和 span

`HTML` 可以通过 `div` 和 `span` 将元素组合起来。

> 大多数 HTML 元素被定义为 **块级元素** 或者 **内联元素**

#### 块级元素
 
> **块级元素在浏览器显示时，通常会以新行来开始（和结束）。**

例如

```html
<h1>
<p>
<ul>
<table>
```

#### 内联元素

> **内联元素在显示时通常不会以新行开始。**

例如：

```
<b>
<td>
<a>
<img>

```

## HTML 中的 div

**`div` 元素是块级元素，占一行。**

```html
<div style="background-color: red;">
    这是一个 div，块级元素
</div>
```


## HTML 中的 span

**`span` 元素是内联元素，可用作文本的容器。**

```html
<span style="background-color: green;">
    这是一个span，内联元素
</span>
```


## HTML 表单和输入

> HTML 表单用于收集不同类型的用户输入

文本输入字段

```html
<input type="text" name="age"/>
```

密码字段

```html
<input type="password" name="password"/>
```

#### 表单

```html

<form action="" method="post">
    <label for="username">用户名</label>
    <input type="text" name="username" id="username">
    <br>

    <label for="password">密码</label>
    <input type="password" name="password" id="password">

</form>
```

![](images/form1.png)


#### 单选按钮

```html
<input type="radio" name="sex" value="男" checked> 男
<input type="radio" name="sex" value="女">女
```

**注意：单选按钮要实现单选，`name` 属性必须一致。**

> 默认选中用 `checked` 来进行标识。


#### 复选框

```html
<h1>复选框</h1>
<input type="checkbox" name="color" value="红色" checked>红色
<input type="checkbox" name="color" value="黄色"> 黄色
<input type="checkbox" name="color" value="绿色"> 绿色
<input type="checkbox" name="color" value="白色"> 白色
```

**注意：`name` 属性必须一致。**

> 默认选中用 `checked` 来进行标识。


#### 提交按钮和重置按钮

```html
<input type="submit" value="提交"/>
<input type="reset" value="重置"/>
```

> 提交按钮用来提交表单，重置按钮用来重置表单中的输入数据，使之成为默认值。


#### 文本域

```html
<textarea style="resize: none;" name="comments" id="comment" cols="30" rows="10"></textarea>

```


## HTML 脚本

`JavaScript` 使 `HTML` 页面具有更强的动态和交互性。

向页面输出 `hello world`

```javascript
<script>
    document.write("hello world");
</script>
```

#### script 标签

- `<script>` 标签用于定义客户端脚本，比如 `JavaScript`。
- `<script>` 元素既可包含脚本语句，也可通过 `src` 属性指向外部脚本文件。
- `JavaScript` 最常用于图片操作、表单验证以及内容动态更新。



















