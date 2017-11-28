# SSH 整合


**Hibernate 版本 5.2.10**

**Struts2 版本 2.5.1**

**Spring 版本 4.3.12**

----



## 1. 添加依赖


> 利用 gradle 构建，引入整合需要的依赖：

```
// hibernate 相关
// https://mvnrepository.com/artifact/org.hibernate/hibernate-core
compile group: 'org.hibernate', name: 'hibernate-core', version: '5.2.10.Final'


//Struts 相关
// https://mvnrepository.com/artifact/org.apache.struts/struts2-core
compile group: 'org.apache.struts', name: 'struts2-core', version: '2.5.1'
// https://mvnrepository.com/artifact/org.apache.struts/struts2-convention-plugin
compile group: 'org.apache.struts', name: 'struts2-convention-plugin', version: '2.5.1'
// https://mvnrepository.com/artifact/org.apache.struts/struts2-spring-plugin
compile group: 'org.apache.struts', name: 'struts2-spring-plugin', version: '2.5.1'




//mysql 连接
// https://mvnrepository.com/artifact/mysql/mysql-connector-java
compile group: 'mysql', name: 'mysql-connector-java', version: '6.0.6'



// Spring 相关
// https://mvnrepository.com/artifact/org.springframework/spring-core
compile group: 'org.springframework', name: 'spring-core', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-context
compile group: 'org.springframework', name: 'spring-context', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-beans
compile group: 'org.springframework', name: 'spring-beans', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-aop
compile group: 'org.springframework', name: 'spring-aop', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-web
compile group: 'org.springframework', name: 'spring-web', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-tx
compile group: 'org.springframework', name: 'spring-tx', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-expression
compile group: 'org.springframework', name: 'spring-expression', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-context-support
compile group: 'org.springframework', name: 'spring-context-support', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-aspects
compile group: 'org.springframework', name: 'spring-aspects', version: '4.3.12.RELEASE'
// https://mvnrepository.com/artifact/org.springframework/spring-orm
compile group: 'org.springframework', name: 'spring-orm', version: '4.3.12.RELEASE'




// c3p0连接池
// https://mvnrepository.com/artifact/com.mchange/c3p0
compile group: 'com.mchange', name: 'c3p0', version: '0.9.5.2'


//日志相关
// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
compile group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.8.2'
// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
compile group: 'org.slf4j', name: 'slf4j-api', version: '1.7.25'


// servlet
// https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
compile group: 'javax.servlet', name: 'javax.servlet-api', version: '3.1.0'

```
----

## 2. Spring 和 Struts 整合

**struts2.xml 配置文件内容**

```
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
        "http://struts.apache.org/dtds/struts-2.5.dtd">

<struts>
    <constant name="struts.i18n.encoding" value="UTF-8"/>
    <!-- 是否为开发模式，支持 struts 提供的 debug 功能 ，生产环境下需要设置为false-->
    <constant name="struts.devMode" value="true"/>
    <constant name="struts.action.extension" value="do,,"/>
    <!-- 配置文件修改后，是否重新加载配置文件， 生产环境下需要设置为false -->
    <constant name="struts.configuration.xml.reload" value="true"/>
    <!-- 上传文件最大字节数-->
    <constant name="struts.multipart.maxSize" value="20971520"/>
    

    <!-- 指定由 spring 负责 action 的创建-->
    <constant name="struts.objectFactory" value="spring"/>

    <!-- 是否启用动态方法访问 -->
    <!--<constant name="struts.enable.DynamicMethodInvocation" value="false"/>-->

    <!-- 浏览器是否缓存静态内容，生产环境下需要设置为 true-->
    <constant name="struts.serve.static.browserCache" value="false"/>


    <!--<package name="user" namespace="/" extends="struts-default">-->
        <!--写上 spring 配置中 UserAction 的 id 值-->
        <!--<action name="user_*" class="userAction" method="{1}">-->
            <!--<result name="success"/>-->
        <!--</action>-->
    <!--</package>-->
</struts>
```

因为这里使用了 `struts2` 的注解，所以这里就没有对 `Action` 进行配置。

> 使用 `struts2` 的注解需要的依赖

```
compile group: 'org.apache.struts', name: 'struts2-convention-plugin', version: '2.5.1'

```

`Action` 类的代码：这里使用了模型驱动的方式来获取参数。

> `Action` 必须被设置成 `prototype`
> 
> `ParentPackage("struts-default") ` 等同于配置文件中的 `extends="struts-default"`
> 
> `namespace` 就是配置文件中的 `namespace`
> 
> `Results`就是对应配置文件中的 `result` 标签
> 
> `@Result(name = "success", location = "/WEB-INF/jsp/index.jsp")` 等同于 `type`
> 
> 方法上的 `@Action`注解等同于配置文件中的 `<action name = "user_*" ...>`部分

```
@Scope(scopeName = "prototype")
@Controller(value = "userAction")
@ParentPackage("struts-default")
@Namespace("/")
@Results({
        @Result(name = "success", location = "/WEB-INF/jsp/index.jsp"),
        @Result(name = "login", location = "/WEB-INF/jsp/user_login.jsp")
})
public class UserAction extends ActionSupport implements ModelDriven<User> {

    @Resource
    private UserService userService;

    private User user = new User();

    // http://localhost:8080/tmall/register.do
    @Action("register")
    public String register(){

        System.out.println("web 层保存数据");

        userService.userRegister(user);

        return NONE;
    }


    @Action("login")
    public String login(){
        System.out.println("web 层登录");

        return NONE;
    }

    @Override
    public User getModel() {
        return user;
    }
}

```

**`Action `交给 `Spring` 去管理，即也就是完成了 `Spring` 和 `Struts`的整合。**

> `Spring` 通过扫描 `@Controller` 注解来为 `XxxAction` 对象生成实例，实例名称一般为 `xxxAction`，当然也可以自己指定生成的实例名称。
> 
> `Spring` 通过 `<context:component-scan base-package="com.lqwit.tmall"/>` 来扫描 `@Controller`，`@Service` 以及`@Repository` 注解。
> 

----
## 2. Spring 和 hibernate 整合

`Spring` 的核心配置文件 `applicationContext.xml`

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd">


       <!--配置 c3p0 连接池 -->
       <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
              <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/tmall"/>
              <property name="user" value="root"/>
              <property name="password" value="student"/>
              <property name="driverClass" value="com.mysql.jdbc.Driver"/>
       </bean>

       <!-- 自动扫描注解-->
       <context:component-scan base-package="com.lqwit.tmall"/>
       <!-- 注解配置-->
       <context:annotation-config/>

       <!-- 加载 hibernate 的核心配置文件-->
       <bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
              <!--<property name="configLocation" value="classpath:hibernate.cfg.xml"/>-->
              <property name="dataSource" ref="dataSource"/>
              <property name="hibernateProperties">
                     <props>
                            <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
                            <prop key="hibernate.show_sql">true</prop>
                            <prop key="hibernate.format_sql">true</prop>
                            <prop key="hibernate.hbm2ddl.auto">update</prop>
                     </props>
              </property>

              <!-- 如果使用注解就扫描该包下面的实体，反之就加载下面的映射文件-->
              <property name="packagesToScan">
                     <list>
                            <value>com.lqwit.tmall.entity</value>
                     </list>
              </property>

              <!-- 加载映射文件 -->
              <!--<property name="mappingResources">-->
                    <!--<list>-->
                           <!--<value>User.hbm.xml</value>-->
                    <!--</list>-->
              <!--</property>-->

       </bean>

       <bean id="hibernateTemplate" class="org.springframework.orm.hibernate5.HibernateTemplate">
              <property name="sessionFactory" ref="sessionFactory"/>
       </bean>


       <!-- 配置平台事务管理器-->
       <bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
              <property name="sessionFactory" ref="sessionFactory"/>
       </bean>
       <!-- 开启事务的注解 -->
       <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>

```

`hibernate` 的核心配置文件 `hibernate.cfg.xml` 中的内容全部都配置在了 `spring` 的配置文件中，所以 `hibernate.cfg.xml` 这个配置文件就不需要了。


----
## 3. web.xml
首先需要配置 `struts.xml` 的核心过滤器，我这里是 `2.5.1` 版本，不同的 `struts` 版本的核心过滤器不一样。

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

另外还需要配置编码过滤器

```
<!--请求编码配置 -->
<filter>
    <filter-name>encodingFileter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>encodingFileter</filter-name>
    <url-pattern>*.jsp</url-pattern>
</filter-mapping>
<filter-mapping>
    <filter-name>encodingFileter</filter-name>
    <url-pattern>*.json</url-pattern>
</filter-mapping>

```

最后加上加载 `spring` 配置文件以及 `spring` 的监听

```

<!-- 加载 spring 配置文件-->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
    

<!-- 配置 spring 监听-->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
    
```
----
## 4. 日志文件 log4j.properties

```
log4j.rootLogger = info,stdout,D,E

#配置stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss,SSS} [%p]-[%l] %m%n

#配置D 保存info debug级别的系统日志信息
log4j.appender.D = org.apache.log4j.DailyRollingFileAppender
#/Users/xxx/mms/log.log 指定info debug级别日志信息存储位置
#log4j.appender.D.File = /Users/xxx/mms/log.log
log4j.appender.D.Append = true
log4j.appender.D.Threshold = INFO,DEBUG
log4j.appender.D.layout = org.apache.log4j.PatternLayout
log4j.appender.D.layout.ConversionPattern = %d{yyyy-MM-dd HH\:mm\:ss,SSS} [%p]-[%l] %m%n

#配置E 保存系统异常日志 
log4j.appender.E = org.apache.log4j.DailyRollingFileAppender
#/Users/xxx/mms/error.log 指定info debug级别日志信息存储位置
#log4j.appender.E.File = /Users/xxx/mms/error.log
log4j.appender.E.Append = true
log4j.appender.E.Threshold = ERROR
log4j.appender.E.layout = org.apache.log4j.PatternLayout
log4j.appender.E.layout.ConversionPattern = %d{yyyy-MM-dd HH\:mm\:ss,SSS} [%p]-[%l] %m%n

#log4j.logger.org.hibernate=INFO
#
## Log all JDBC parameters
#log4j.logger.org.hibernate.type=ALL

##Hibernate begin 打印每次数据访问产生的sql语句至log.log 文件当中##
log4j.logger.org.hibernate=info
#配置SQL打印与输出
log4j.logger.org.hibernate.SQL=DEBG
log4j.logger.org.hibernate.HQL=DEGUG
#log4j.logger.org.hibernate.type=ALL

```

----
## 5. 大功告成
`userDaoImpl.java` 代码

```
@Repository
public class UserDaoImpl implements UserDao {

    @Resource(name = "hibernateTemplate")
    private HibernateTemplate hibernateTemplate;


    @Override
    public void userRegister(User user) {
        List<User> fromUser = (List<User>) hibernateTemplate.find("from User");
        for (User user1: fromUser) {
            System.out.println(user1);
        }
    }
}
```


`service` 层的代码就不看了。就是 `Action` 调用 `Service` 层的方法， `service` 层调用 `dao` 层的方法，`dao` 层去操作数据库。


**包结构图**

![ssh 整合包结构图](ssh.png)