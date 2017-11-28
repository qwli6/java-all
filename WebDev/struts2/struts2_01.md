----------
	
# Struts2 快速入门

### 1. 基本配置
	
创建 `WEB` 项目，编写 `JSP` 的页面，编写超链接，点击超链接发送请求，请求服务器，让服务器的方法去执行！

```
<h3>Struts2的入门程序</h3>
<a href="${ pageContext.request.contextPath }/hello.action">访问 action </a>
```
	
利用 `maven` 或者 `gradle` 引入 `struts2` 的依赖

```
// https://mvnrepository.com/artifact/org.apache.struts/struts2-core
compile group: 'org.apache.struts', name: 'struts2-core', version: '2.5.1'
```

在 `web.xml` 文件中配置 `struts2` 的核心过滤器，必须配置，`StrutsPrepareAndExecuteFilter` 是 `struts2` 的核心控制器。

```
 <filter>
	<filter-name>struts2</filter-name>
	<filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>struts2</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>

```

> 注意：早前的版本核心控制器可能是这样的配置：

```
<filter>
	<filter-name>struts2</filter-name>
	<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>struts2</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>

```
	 	
----------
	
### 2. 编写Action类

`Action` 类是动作类，是 `Struts2` 处理请求，封装数据，响应页面的核心控制器。需要自己编写。

```
public String sayHello(){
	System.out.println("Hello Struts2!!");
	return null;
}

```
	
----------
	
### 3. 编写 Struts2 的核心配置文件
	
1. 配置文件名称是 `struts.xml`（名称必须是 `struts.xml`）
2. 在 `src` 下引入 `struts.xml` 配置文件（配置文件的路径必须是在 `src` 的目录下）
3. 配置如下

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
	"-//Apache Software Foundation//DTD Struts Configuration 2.3//EN"
	"http://struts.apache.org/dtds/struts-2.3.dtd">

<struts>
    <package name="default" namespace="/" extends="struts-default">
        <action name="hello" class="com.lqwit.action.HelloAction" method="sayHello">
        </action>
    </package>
</struts>
		
```
	
----------
	
# Struts2 的执行流程
	
### 1. 执行的流程

* 编写的页面，点击超链接，请求提交到服务器端。
* 请求会先经过 `Struts2` 的核心过滤器（StrutsPrepareAndExecuteFilter）
* 过滤器的功能是完成了一部分代码功能
* 就是一系列的拦截器执行了，进行一些处理工作。
* 咱们可以在 `struts-default.xml` 配置文件中看到有很多的拦截器。可以通过断点的方式来演示。
* 拦截器执行完后，会根据 `struts.xml` 的配置文件找到请求路径，找到具体的类，通过反射的方式让方法执行。
	
### 2. 总结

* `JSP` 页面--> `StrutsPrepereAndExecuteFilter` 过滤器-->执行一系列拦截器（完成了部分代码）-->执行到目标 `Action` -->返回字符串-->结果页面（result）-->页面跳转
	
----
	
# `Struts2`框架配置文件加载顺序
	

### 1. `Struts2` 框架的核心是 `StrutsPrepareAndExecuteFilter` 过滤器，该过滤器有两个功能
* Prepare		-- 预处理，加载核心的配置文件
* Execute		-- 执行，让部分拦截器执行
	
### 2. `StrutsPrepareAndExecuteFilter` 过滤器会加载哪些配置文件？
* 通过源代码可以看到具体加载的配置文件和加载配置文件的顺序

```
init_DefaultProperties(); 				-- 加载 org/apache/struts2/default.properties

init_TraditionalXmlConfigurations();		-- 加载 struts-default.xml,struts-plugin.xml,struts.xml

init_LegacyStrutsProperties();			-- 加载自定义的 struts.properties.

init_CustomConfigurationProviders();		-- 加载用户自定义配置提供者

init_FilterInitParameters() ;				-- 加载 web.xml
```
	
### 3. 重点了解的配置文件

```
default.properties		-- 在 org/apache/struts2/ 目录下，代表的是配置的是 Struts2的常量的值
struts-default.xml		-- 在 Struts2 的核心包下，代表的是 Struts2 核心功能的配置（Bean、拦截器、结果类型等）
struts.xml				-- 重点中的重点配置，代表WEB应用的默认配置，在工作中，基本就配置它就可以了！！（可以配置常量）
web.xml					-- 配置前端控制器（可以配置常量）

```
		
>  注意：
> 
> 前3个配置文件是struts2框架的默认配置文件，基本不用修改。
> 
> 后3个配置文件可以允许自己修改struts2的常量。但是有一个特点：后加载的配置文件修改的常量的值，会覆盖掉前面修改的常量的值。
	
### 4. 总结

* 先加载 `default.properties` 文件，在 `org/apache/struts2/default.properties` 文件，都是常量。
* 又加载 `struts-default.xml` 配置文件，在核心的`jar`包最下方，`struts2`框架的核心功能都是在该配置文件中配置的。
* 再加载`struts.xml`的配置文件，在`src`的目录下，代表用户自己配置的配置文件
* 最后加载`web.xml`的配置文件

* **后加载的配置文件会覆盖掉之前加载的配置文件（在这些配置文件中可以配置常量）**

----------
	
# `struts.xml`配置文件的配置

### 1. `<package>`标签，如果要配置`<Action>`的标签，那么必须要先配置`<package>`标签，代表的包的概念
* 包含的属性
	* name					-- 包的名称，要求是唯一的，管理`action`配置
	* extends				-- 继承，可以继承其他的包，只要继承了，那么该包就包含了其他包的功能，一般都是继承`struts-default`
	* namespace				-- 名称空间，一般与`<action>`标签中的`name`属性共同决定访问路径（通俗话：怎么来访问`action`），常见的配置如下
	* namespace="/"		-- 根名称空间
	* namespace="/aaa"	-- 带有名称的名称空间
   * abstract				-- 抽象的。这个属性基本很少使用，值如果是`true`，那么编写的包是被继承的
	
### 2. `<action>`标签
* 代表配置 `action` 类，包含的属性
	* name			-- 和`<package>`标签的`namespace`属性一起来决定访问路径的
	* class			-- 配置`Action`类的全路径（默认值是`ActionSupport`类）
	* method		-- `Action`类中执行的方法，如果不指定，默认值是`execute`
	
### 3. `<result>`标签
* `action`类中方法执行，返回的结果跳转的页面
	* name		-- 结果页面逻辑视图名称
	* type		-- 结果类型（默认值是转发，也可以设置其他的值）
	
----------
	
# `Struts2`配置常量
	
### 1. 可以在`Struts2`框架中的哪些配置文件中配置常量？
* struts.xml（必须要掌握，开发中基本上就在该配置文件中编写常量）

```
<constant name="key" value="value"></constant>
```

* web.xml

```
在 StrutsPrepareAndExecuteFilter 配置文件中配置初始化参数
```
* 注意：后加载的配置的文件的常量会覆盖之前加载的常量！！
	
### 2. 了解的常量

```
struts.i18n.encoding=UTF-8			
-- 指定默认编码集，作用于`HttpServletRequest`的setCharacterEncoding方法 
```

```
struts.action.extension=action,,
-- 该属性指定需要 `Struts 2`处理的请求后缀，该属性的默认值是`action`，即所有匹配
```
> *.action的请求都由Struts2处理。如果用户需要指定多个请求后缀，则多个后缀之间以英文逗号（,）隔开

```		
struts.serve.static.browserCache=true
-- 设置浏览器是否缓存静态内容,默认值为true(生产环境下使用),开发阶段最好关闭 
```

```
struts.configuration.xml.reload=false	
-- 当struts的配置文件修改后,系统是否自动重新加载该文件,默认值为false(生产环境下使用) 
```
```
struts.devMode = false			
开发模式下使用,这样可以打印出更详细的错误信息 
```
	
----------
	
**入门总结之指定多个struts的配置文件（了解）**
	
	1. 在大部分应用里，随着应用规模的增加，系统中Action的数量也会大量增加，导致struts.xml配置文件变得非常臃肿。
		为了避免struts.xml文件过于庞大、臃肿，提高struts.xml文件的可读性，我们可以将一个struts.xml配置文件分解成多个配置文件，然后在struts.xml文件中包含其他配置文件。
	
	2. 可以在<package>标签中，使用<include>标签来引入其他的struts_xx.xml的配置文件。例如：
		<struts>
			<include file="struts-part1.xml"/>
			<include file="struts-part2.xml"/>
		</struts>
	
	3. 注意注意注意（重要的事情说三遍）：<include file="cn/itcast/demo2/struts-part1.xml"/>
	
----------
	
**技术分析之Action类的三种写法**
	
	1. 配置文件学习完成，下面的重点是Action类的三种写法
		* Action类就是一个POJO类
			* 什么是POJO类，POJO（Plain Ordinary Java Object）简单的Java对象.简单记：没有继承某个类，没有实现接口，就是POJO的类。
		
		* Action类可以实现Action接口
			* Action接口中定义了5个常量，5个常量的值对应的是5个逻辑视图跳转页面（跳转的页面还是需要自己来配置），还定义了一个方法，execute方法。
			* 大家需要掌握5个逻辑视图的常量
				* SUCCESS		-- 成功.
				* INPUT			-- 用于数据表单校验.如果校验失败,跳转INPUT视图.
				* LOGIN			-- 登录.
				* ERROR			-- 错误.
				* NONE			-- 页面不转向.
		
		* Action类可以去继承ActionSupport类（开发中这种方式使用最多）
			* 设置错误信息
	
----------
	
**技术分析之Action的访问**
	
	1. 通过<action>标签中的method属性，访问到Action中的具体的方法。
		* 传统的配置方式，配置更清晰更好理解！但是扩展需要修改配置文件等！
		* 具体的实例如下：
			* 页面代码
				* <a href="${pageContext.request.contextPath}/addBook.action">添加图书</a>
				* <a href="${pageContext.request.contextPath}/deleteBook.action">删除图书</a>
			
			* 配置文件的代码
				<package name="demo2" extends="struts-default" namespace="/">
			    	<action name="addBook" class="cn.itcast.demo2.BookAction" method="add"></action>
			    	<action name="deleteBook" class="cn.itcast.demo2.BookAction" method="delete"></action>
			    </package>
			
			* Action的代码
				public String add(){
					System.out.println("添加图书");
					return NONE;
				}
				public String delete(){
					System.out.println("删除图书");
					return NONE;
				}
	
	2. 通配符的访问方式:(访问的路径和方法的名称必须要有某种联系.)	通配符就是 * 代表任意的字符
		* 使用通配符的方式可以简化配置文件的代码编写，而且扩展和维护比较容易。
		* 具体实例如下：
			* 页面代码
				<a href="${pageContext.request.contextPath}/order_add.action">添加订单</a>
				<a href="${pageContext.request.contextPath}/order_delete.action">删除订单</a>
			
			* 配置文件代码
				* <action name="order_*" class="cn.itcast.demo2.OrderAction" method="{1}"></action>
			
			* Action的代码
				public String add(){
					System.out.println("添加订单");
					return NONE;
				}
				public String delete(){
					System.out.println("删除订单");
					return NONE;
				}
			
		* 具体理解：在JSP页面发送请求，http://localhost/struts2_01/order_add.action，配置文件中的order_*可以匹配该请求，*就相当于变成了add，method属性的值使用{1}来代替，{1}就表示的是第一个*号的位置！！所以method的值就等于了add，那么就找到Action类中的add方法，那么add方法就执行了！
	
	3. 动态方法访问的方式（有的开发中也会使用这种方式）
		* 如果想完成动态方法访问的方式，需要开启一个常量，struts.enable.DynamicMethodInvocation = false，把值设置成true。
			* 注意：不同的Struts2框架的版本，该常量的值不一定是true或者false，需要自己来看一下。如果是false，需要自己开启。
			* 在struts.xml中开启该常量。
				* <constant name="struts.enable.DynamicMethodInvocation" value="true"></constant>
		
		* 具体代码如下
			* 页面的代码
				* <a href="${pageContext.request.contextPath}/product!add.action">添加商品</a>
				* <a href="${pageContext.request.contextPath}/product!delete.action">删除商品</a>
			
			* 配置文件代码
				* <action name="product" class="cn.itcast.demo2.ProductAction"></action>
			
			* Action的类的代码
				public class ProductAction extends ActionSupport{
					public String add(){
						System.out.println("添加订单");
						return NONE;
					}
					public String delete(){
						System.out.println("删除订单");
						return NONE;
					}
				}
	
----------
	
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          