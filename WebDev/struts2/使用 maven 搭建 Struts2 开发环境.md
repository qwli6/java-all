使用 Maven 搭建 Struts2 开发环境


> 开发工具：Intellij
> 构建工具：Maven

----

## 工程截图

工程截图如下

![Maven 工程截图](http://upload-images.jianshu.io/upload_images/2658684-1d9317863a33a4f5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)

指定 tomcat7 运行插件（pom.xml）

```
 <build>
        <plugins>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <path>/mavenTest</path>
                    <port>8080</port>
                    <uriEncoding>UTF-8</uriEncoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

添加 Struts2 依赖

```
 <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <struts2.version>2.5.13</struts2.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.apache.struts</groupId>
            <artifactId>struts2-core</artifactId>
            <version>${struts2.version}</version>
        </dependency>
    </dependencies>

```
pom.xml 文件中其他配置

```
 <groupId>com.lqwit</groupId> //指定项目组，一般是公司域名反转
 
 <artifactId>mavenTest</artifactId> //指定项目名称
    
 <version>1.0-SNAPSHOT</version> //项目版本信息
    
 <packaging>war</packaging> //打包方式
```
> 打包方式一般分为三种，聚合工程时的父工程打包方式为 pom，如果是 java 工程，打包方式则为 jar，如果是 web 工程，打包方式则为 war。

---
##  添加 Struts2 核心配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
        "http://struts.apache.org/dtds/struts-2.5.dtd">
<struts>
    <!-- 指定编码规则 -->
    <constant name="struts.i18n.encoding" value="UTF-8"/>
    <!--  开发模式，报错可以在浏览器中看到具体信息 -->
    <constant name="struts.devMode" value="true"/>
    <!-- 配置扩展名-->
    <constant name="struts.action.extension" value="do,,"/>

    <package name="user" namespace="/" extends="struts-default">
        <action name="save" class="com.lqwit.maven.action.HelloAction" method="save">
            <result name="success">/index.jsp</result>
        </action>
    </package>
</struts>
```
---
## 在 web.xml 文件中配置 struts2 核心过滤器

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

    <filter>
        <filter-name>struts2</filter-name>
        <filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>struts2</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```
注意：这里 filter-class 是新版本的，以前版本的 struts2 的核心过滤器类为：

```
org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter
```

---
## 编写 Action 类
> Action 类继承 ActionSupport 

```
public class HelloAction extends ActionSupport{
    public String save() throws  Exception{
        System.out.println("访问 save action");
        return SUCCESS;
    }
}
```

## 运行测试
![编写 maven 运行指令](http://upload-images.jianshu.io/upload_images/2658684-afc64b3a16446abf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)

![编写 maven 运行指令](http://upload-images.jianshu.io/upload_images/2658684-93c3e529f9ce2b19.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)
