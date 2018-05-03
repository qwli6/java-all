## HTML 文本格式化

- 加粗文本

```html
<b>加粗文本</b>
<strong>加粗文本</strong>
```

- 斜体

```html
<em>斜体文字</em>
<i>斜体文字</i>
```

- 删除线

```html
<s>删除线</s>
```

> 通常用标签 `<strong></strong>` 来替换 `<b>` 来使用，`<em>` 替换 `<i>` 标签来使用。
> `<strong>` 或者 `<em>` 意味着你要呈现的文本是重要的，所以要突出显示。

- 小号字

```html
<small>小号字</small>
```

- 上下标

```html
<!-- 下标-->
2<sub>3</sub><br>

<!-- 上标-->
3<sup>2</sup><br>
```


## HTML 计算机输出标签

- `code` 标签

定义这是计算机的代码，例如 `Java`，`Python`

```html
<code></code>
```

```html
//引用
<blockquote></blockquote>

```


## HTML 中的图像

`height（高度）` 与 `width（宽度）`属性用于设置图像的高度和宽度。

```html
 <img src="pulpit.jpg" alt="这是一张图片" width="304" height="228">
```

**属性值默认单位为像素 `px`**


> 注意：指定图像的高度和宽度是一个很好的习惯，如果图像指定了高度宽度，页面加载时就会保留指定的尺寸。如果没有指定图片的大小，加载页面时有可能会破坏 `HTML` 页面的整体布局。


## HTML 中的表格

表格由 `<table>` 标签来定义。
每个表格均有若干行（由 `<tr>` 标签定义），每行被分割为若干单元格（由 `<td>` 标签定义）。字母 `td` 指表格数据（`table data`），即数据单元格的内容。
数据单元格可以包含文本、图片、列表、段落、表单、水平线、表格等等。

```html
<table border="1">
    <tr>
        <td>第一行第一列</td>
        <td>第一行第二列</td>
    </tr>
    <tr>
        <td>第二行第一列</td>
        <td>第二行第二列</td>
    </tr>
</table>
```

> `border = "1"` 表示该表格有边框

![](images/table1.png)


#### HTML 中的表格显示表头

```
<table border="1">
    <tr>
        <th>姓名</th>
        <th>性别</th>
    </tr>
    <tr>
        <td>张三</td>
        <td>男</td>
    </tr>
    <tr>
        <td>李四</td>
        <td>女</td>
    </tr>
</table>
```

![](images/table2.png)

#### HTML 跨列显示

```html
<table border="1">
    <tr>
        <th>姓名</th>
        <th>性别</th>
        <th>电话</th>
    </tr>
    <tr>
        <td>张三</td>
        <td>男</td>
        <td>110</td>
    </tr>
    <tr>
        <td colspan="2">李四</td>
    </tr>
</table>

```
![](images/table3.png)


> `colspan="2"` 表示该单元格跨两列显示
> `rowspan="2"` 表示该单元格跨两行显示


## 小练习-利用 HTML 实现高中课程表

完整源码如下

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="selfassu"/>
    <meta name="description" content="这是一份高中课程表"/>
    <meta name="keywords" content="高中课程表，课表"/>
    <!--3 秒自动刷新页面-->
    <meta http-equiv="refresh" content="3"/>
    <title>利用 table 属性实现高中课程表</title>
</head>
<body>
<table border="1">
    <tr>
        <th colspan="2">星期/节次</th>
        <th>星期一</th>
        <th>星期二</th>
        <th>星期三</th>
        <th>星期四</th>
        <th>星期五</th>
        <th>星期六</th>
    </tr>
    <tr>
        <td rowspan="5" align="center">上午</td>
        <td>早自习</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
    </tr>
    <tr>
        <td>第一节</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
    </tr>
    <tr>
        <td>第二节</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
    </tr>
    <tr>
        <td>第三节</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
    </tr>
    <tr>
        <td>第四节</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
        <td>语文</td>
        <td>英语</td>
    </tr>
    <tr>
        <td rowspan="3">下午</td>
        <td>第一节</td>
        <td>语文</td>
        <td>化学</td>
        <td>物理</td>
        <td>生物</td>
        <td>德育</td>
        <td>政治</td>
    </tr>
    <tr>
        <td>第二节</td>
        <td>语文</td>
        <td>化学</td>
        <td>物理</td>
        <td>生物</td>
        <td>德育</td>
        <td>政治</td>
    </tr>
    <tr>
        <td>第三节</td>
        <td>语文</td>
        <td>化学</td>
        <td>物理</td>
        <td>生物</td>
        <td>德育</td>
        <td>政治</td>
    </tr>
    <tr>
    <tr>
        <td rowspan="1">晚上</td>
        <td>晚自习</td>
        <td>数学</td>
        <td>化学</td>
        <td>物理</td>
        <td>生物</td>
        <td>德育</td>
        <td>政治</td>
    </tr>

</table>
</body>
</html>

```

效果图如下

![](images/table4.png)


## HTML 列表

有序列表和无序列表

```html
<ul>
    <li>第一个无序列表项</li>
    <li>第二个无序列表项</li>
    <li>第三个无序列表项</li>
</ul>
<br>
<ol>
    <li>第一个有序列表项</li>
    <li>第二个有序列表项</li>
    <li>第三个有序列表项</li>
</ol>
```

嵌套列表

```
<ul>
    <li>列表项一</li>
    <li>列表项二
        <ol>
            <li>子列表一</li>
            <li>子列表二</li>
        </ol>
    </li>
    <li>列表项三</li>
</ul>
```












