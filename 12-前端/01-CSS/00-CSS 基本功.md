## HTML 样式 - CSS

`CSS (Cascading Style Sheets)` 用于渲染 `HTML` 元素标签的样式。

#### 什么是 CSS

- `CSS` 指层叠样式表 `(Cascading Style Sheets)`
- 样式定义如何显示 `HTML` 元素
- 样式通常存储在样式表中
- 把样式添加到 `HTML 4.0` 中，是为了解决内容与表现分离的问题
- 外部样式表可以极大提高工作效率
- 外部样式表通常存储在 `CSS` 文件中
- 多个样式定义可层叠为一个


## 如何使用 CSS

`CSS` 是在 `HTML 4` 开始使用的,是为了更好的渲染 `HTML` 元素而引入的。

`CSS` 可以通过以下方式添加到 `HTML` 中:

- 内联样式- 在 `HTML`元素中使用 `style` 属性
- 内部样式表 -在 `HTML`文档头部 `<head>` 区域使用 `<style>` 元素来包含 `CSS`
- 外部引用 - 使用外部 `CSS` 文件

**最好的方式是通过外部引用 `CSS`文件。**

> 注意：在这里我不学习其他引入样式的方式，只学习外部引入样式的方式。


```css
body{
    background-color: #666666;
}

h1{
    color: orange;
    text-align: center;
}

p{
    font-family:"Times New Roman";
    font-size: 20px;
}
```

在 	`html` 的 `head` 标签中用 `link` 引入

```html
<link rel="stylesheet" href="css/index.css"/>

```


## CSS 的 id 选择器和 class 选择器

**如果你要在 `HTML` 元素中设置 `CSS` 样式，你需要在元素中设置 "id" 和 "class" 选择器。**

- 利用 `id` 选择器操作 `css`

```html
<span id="text111">我是 id 选择器</span>
```

```css
#text111 {
    font-family: "Times New Roman";
    font-size: 12px;
    color: red;
}
```

- 利用 `class` 选择器操作 `css`

```html
<span class="text222">我是 class 选择器</span>
```

```css
.text222 {
    font-family: "Times New Roman";
    font-size: 18px;
    color: greenyellow;
}
```

![](images/id+class.png)


> **类名的第一个字符不能使用数字！它无法在 `Mozilla` 或 `Firefox` 中起作用。**

## CSS 背景

`css` 背景属性用于定义 `html` 元素的背景。

`css` 属性定义背景效果：

```
//定义背景颜色
background-color
//定义背景图片   
background-image
//定义背景重复度
background-repeat

```

例如：

```
//定义 html 整个背景颜色为 #666666
body{
    background-color: #666666;
}

```

## CSS 链接

链接的样式，可以用任何 `CSS` 属性（如：颜色，字体，背景等）。
特别的连接，可以有不的样式，这取决于他们是什么状态：

- a:link  正常，未访问过的链接
- a:visited 用户已经访问过的链接
- a:hover  当用户鼠标放在链接上时
- a:active 连接被点击的那一刻

```html
a:link{
    color: #000000;
    text-decoration:none;
    //指定链接的背景颜色
    background-color:#B2FF99;
}

a:visited{
    color: #0F0;
    text-decoration:none;
}

a:hover{
    color: #F0F;
    text-decoration:none;
}

a:active{
    color: #0000FF;
    text-decoration:none;
}

```

> 注意：
> 当设置为若干链路状态的样式，也有一些顺序规则：
>
> a:hover 必须跟在 a:link 和 a:visited后面
> a:active 必须跟在 a:hover后面


## CSS 盒子模型

![](images/box-model.png)

说明：

> - Margin(外边距) - 清除边框外的区域，外边距是透明的。
> - Border(边框) - 围绕在内边距和内容外的边框。
> - Padding(内边距) - 清除内容周围的区域，内边距是透明的。
> - Content(内容) - 盒子的内容，显示文本和图像。

```
div {
    background-color: lightgrey;
    width: 300px;
    border: 25px solid green;
    padding: 25px;
    margin: 25px;
}

```

如图

![](images/margin-padding.png)

> **这个 div 总的宽度为 300 + 25(左边 margin) + 25(左边 padding) + 25（左边线条宽度） + 25(右边线条宽度) + 25(右边 padding) + 25(右边 margin)  = 450px**
> 
> 实际 div 中的内容所占宽度仅仅只有 300px 


#### 盒子模型总结

> 最终元素的总宽度计算公式是这样的：

> **总元素的宽度=宽度+左填充+右填充+左边框+右边框+左边距+右边距**

> 元素的总高度最终计算公式是这样的：

> **总元素的高度=高度+顶部填充+底部填充+上边框+下边框+上边距+下边距**


## CSS 边框（Border）

```
<p class="full-border">
    四边都有边框
</p>

<p class="radius-border">
    圆角边框
</p>


<p class="bottom-border">
    底部有边框
</p>

<p class="reference-border">
    引用边框
</p>
```

css

```
.full-border{
    border: solid 1px #000000;
    padding: 4px;
}

.radius-border{
    border: solid 1px #000000;
    padding:4px;
    border-radius: 4px;
}

.bottom-border{
    border-bottom:solid 1px #000000;
    padding:4px;
}

.reference-border{
    border-left: solid 4px deepskyblue;
    padding: 8px;
    background-color: #eeeeee;
}
```

结果

![](images/border.png)


## Margin 和 Padding

margin 是外边距，padding 是内边距

![](images/margin-padding2.png)


## 元素的显示和隐藏

隐藏元素
> display:none 或者 visibility:hidden
> 
> 
> 注意：**visibility:hidden 可以隐藏某个元素，但隐藏的元素仍需占用与未隐藏之前一样的空间。也就是说，该元素虽然被隐藏了，但仍然会影响布局。**
> 
> **display:none 可以隐藏某个元素，且隐藏的元素不会占用任何空间。也就是说，该元素不但被隐藏了，而且该元素原本占用的空间也会从页面布局中消失。**


## 改变元素显示
块元素是一个元素，独自占一行，前后都是换行。

例如

```
<h1>
<p>
<div>
```

内联元素只需要必要的宽度，不强制换行。

例如：

```
<span>
<a>
```

#### 更改元素的显示

可以更改内联元素和块元素。使用 `display` 属性即可。

下面演示把块元素显示更改成内联元素

```
<ul>
    <li><a href="#">哈哈</a></li>
    <li><a href="#">呵呵</a></li>
    <li><a href="#">嘻嘻</a></li>
</ul>

//css 样式
li{
    display:inline;
}
```

同样如果要把内联元素更改成块元素，直接使用 `display:block` 即可。