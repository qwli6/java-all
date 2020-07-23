## HTML

超文本标记语言（英语：HyperText Markup Language，简称：HTML）是一种用于创建网页的标准标记语言。


> 对于中文网页需要使用 `<meta charset="utf-8">` 声明编码，否则会出现乱码。
> 有些浏览器会设置 `GBK` 为默认编码，则你需要设置为 `<meta charset="gbk">`。

`html` 使用标记标签来描述网页。同样 `html` 文档页面也叫做 `web` 页面。


## HTML 文档的后缀名

`html` 有两种后缀名，分别为 `html`， `htm`， 两种后缀名均可使用。


## HTML 文档解析

 - **`html` 文档最开始的标签为：**

```html
//声明为 html5 文档，这里不区分大小写
<!DOCTYPE html>

```

- **紧接着就是：**

```html
<html> 标签
```

`html` 标签是 `html` 页面的根元素

- **然后是`head`**

```html
<head> 标签
```

`head` 标签中申明了文档的元数据，即 `<meta> 元数据`

> 可以添加到 `head` 中常用的标签元素为：
> `<title>`， `<style>`， `<meta>`，`<link>`，`<script>`


为搜索引擎定义关键词

```html
<meta name="keywords" content="HTML, CSS"/>
```

为网页定义描述内容

```html
<meta name="description" content="这是一个网页描述"/>
```

定义网页作者

```html
<meta name="author" content="lqwit"/>
```

每 `3` 秒刷新当前页面

```html
<meta http-equiv="refresh" content="3"/>
```

- **`title` 标签**

```html
<title> 标签
```

`title` 标签中包含了文档的标题，它定义了浏览器工具的标题。当网页添加到收藏夹时，显示在收藏夹中的标题。同时也是显示在搜索引擎结果页面的标题。

- **`body` 标签**

```html
<body> 标签元素包含了可见的页面内元素，例如 <h1> <p> 等等
```


> 注意： `html` 标签一般都是成对出现的，例如 `<html></html>`， `<title></title>`
> 切记标签一定要闭合，不闭合的标签可能引起灾难性的后果。

```html
<title>这是标题</title>
<p>这是段落</p>
```

## HTML 的网页结构

```html
<html>
	<head>
		<meta ....../>
		<meta ....../>
		<title> 这是标题 </title>
	
	</head>
	<body>
		<h1>这是一个标题</h1>
		<p>这是一个段落</p>
		<p>这是另外一个段落</p>
	
	</body>

</html>

```

> 只有 `body` 区域内的部分才会在浏览器中显示。


## HTML 中的标签

> 注意 `html` 中的标签不区分大小写，例如`<p>` 和 `<P>` 是等价的，**但是最好还是用小写来书写**。

- 标题标签

```html
<h1></h1>
<h2></h2>
<h3></h3>
<h4></h4>
<h5></h5>
<h6></h6>
```

> `h1` 是最大的标题，`h6` 是最小的标题。一般标题会占一行，浏览器会自动在标题的前后添加空行

- 段落标签

```html
<p></p>
```

- 超链接标签

```html
<a href="">这是超链接</a>
```

> `a` 标签中包含 `href` 属性，`target` 属性，`href` 属性指定点击 `a` 链接后打开什么页面，target 属性指定在什么地方打开，如果为 `_self` 就表示在自身的窗口中打开，如果是`_blank` 则表示在另一个空白页中打开。
> 
> 常用的标签就这两个，具体可以看下面的例子

```html
// a 标签示例， 在一个新的浏览器窗口中打开百度
href="http://www.baidu.com" target="_blank">点我打开百度</a>

```

- `img` 标签

```
<img src="xxx" alt="这是图片"/>
```

> `src` 表示这里填的是图片的地址， `alt` 表示如果当图片无法显示的时候，该文字会显示。

- `hr` 标签

```html
<hr/>
```

`hr` 标签在 `html` 页面中创建一个水平线。可以用于分割内容。

- `br` 标签

```html
<br/>
```

`br` 标签一般是换行。**注意：这是一个可以闭合，也可以不闭合的标签，如 `<br>`。**

- `p` 标签

```html
<p>这是一个段落</p>
```

`p` 标签一般用于声明一个段落。**注意：浏览器会自动在 `p` 标签的后面添加上一个换行符。（`p` 标签是块级元素）**


## HTML 中的属性

- `html` 元素可以设置属性
- 属性可以在元素中添加附加信息
- 属性一般描述于开始标签
- 属性名称一般是以 `name=value` 的形式出现，也就是键值对。















