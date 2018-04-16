# hibernate 入门

`hibernate` 是一个经典的 `orm`（对象关系映射）框架



### 1. 入门

利用 `idea` 新建一个 `web` 工程，选中 `hibernate` 模块，让 `idea` 自动去帮我们下载所需要的 `jar` 包。此处可能会下载失败，如果下载失败了，换下网络重新下载即可。


### 2. 编写对象映射文件以及实体类

```
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping package = "com.lqwit.domain">
    <class name="User" table="user">
    	 <!--
    	  name: 表示实体类的成员字段名称
    	  column： 表示数据库表中的字段名称
    	 -->
        <id name="uid" column="uid">
            <!-- 主键生成策略-->
            <generator class="native"/>
        </id>

        <property name="name" column="name"/>
        <property name="sex" column="sex"/>
        <property name="address" column="address"/>

    </class>

</hibernate-mapping>
```


实体类

```
package com.lqwit.domain;

public class User {
    private Long uid;
    private String name;
    private String sex;
    private String address;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


```

### 3. 编写核心配置文件 hibernate.cfg.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <!-- 一个数据库值对应一个 session-factory-->
    <session-factory>
        <!--1. 必须的配置 -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="hibernate.connection.url">jdbc:mysql:///mybatis01</property>
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="hibernate.connection.password">student</property>
        <property name="hibernate.connection.username">root</property>


        <!--2. 可选的配置-->

        <!-- 是否显示 sql 语句，在控制台中-->
        <property name="hibernate.show_sql">true</property>
        <!-- 格式化 sql 语句，以便显示更美观-->
        <property name="hibernate.format_sql">true</property>

        <!-- 生成数据库的表结构 -->
        <property name="hibernate.hbm2ddl.auto">update</property>

        
        <!--3. 引入实体映射关系表-->
        <mapping resource="com/lqwit/domain/User.hbm.xml"/>
    </session-factory>
</hibernate-configuration>
```

注意：下面这行代码可以帮助我们去创建数据库的表结构

```
 <property name="hibernate.hbm2ddl.auto">update</property>
```

有几种可选值：（通过控制台打印的 `sql` 语句可以看出来）

> 1. create ：每次执行都创建一张表，没有表就创建，有表就先删除，再创建。(自己测试用)
> 
> 2. create-drop： 表存在的情况下：先删除再创建表，执行完后再删除表。表不存在的情况下：先创建表，执行操作，执行完成后删除表。
> 
> 3. update：如果没有表，创建表结构。如果有表结构，不会创建，正常添加数据。`update` 可以用来帮我们更新数据库的字段信息。如果需要加字段，直接在实体类和映射文件中完善就可以了。但是不能帮你删除字段。删除字段需要手动执行 `alter table user drop column1`;
>
>4. validate: 校验。校验实体类字段和数据库字段是不是一一对应的。



### 4. 测试

```
@Test
    public void textSave(){
      //  Configuration configuration = new Configuration();
      //  configuration.configure("hibernate.cfg.xml");
        
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        
        // 如果 hibernate.cfg.xml 在 src 目录下，应该执行如下
        // 默认加载 src 目录下的 hibernate 核心配置文件
        // configuration.configure();


        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User user = new User();

        user.setName("小王2");
        user.setSex("男2");
        user.setAddress("湖北沧州2");
        session.save(user);


        transaction.commit();
        session.close();

    }
}
```

### 5. 输出结果

```
Hibernate: insert into user (name, sex, address) values (?, ?, ?)
```


# SessionFactory 
`SessionFactory` 负责初始化 `Hibernate`。`SessionFactory` 并不是轻量级的，初始化和销毁都需要消耗一定的资源，所以我们只需要为每一个数据库指定一个 `SessionFactory` 即可。

`SessionFactory` 是工厂类，是生成 `Session` 对象的工厂类。

SessionFactory 的特点：

1. 有 `Configuration` 通过加载配置文件创建该对象。
2. `SessionFactory` 对象中保存了当前的数据库配置信息和所有的映射关系以及预定义的 `sql` 语句。同时， `SessionFactory` 还负责维护 `hibernate` 的二级缓存。
3. 一个 `SessionFactory` 实例对应一个数据库，应用从该对象中获取 `Session` 实例。
4. `SessionFactory` 是重量级的，意味着不能随意创建或者销毁它的实例，如果只访问一个数据库，只需要创建一个 `SessionFactory` 即可。
5. `SessionFactory` 是**线程安全**的，意味着它的一个实例可以被应用的多个线程共享。
6. `SessionFactory` 需要一个较大的缓存，用来存放预定义的 `sql` 语句以及实体类的映射信息，另外可以配置一个缓存插件，这个插件被称之为 `hibernate` 的二级缓存，被多线程共享。




**总结：一般应用使用一个 `SessionFactory` ，最好是应用启动的时候就完成初始化。**

> 封装一个 Hibernate SessionFactory 的工具类
> 

```
public class HibernateUtils {

    public static final Configuration configuration;
    public static final SessionFactory sessionFactory;

	//静态代码块，类加载的时候就加载。
    static {
        configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
}

```

# Transaction 接口

`Transaction` 是事务的接口，常用的方法如下

```
commit()   提交事务
rollback() 回滚事务
```

特点：

> `Hibernate` 框架默认情况下事务不自动提交，需要手动提交事务。
> 
> 如果没有开启事务，那么每个 `Session` 的操作，都相当于一个独立的事务。


# 测试常用的方法的单元测试代码

```
@Test
public void testSave2(){

    Session session = HibernateUtils.getSession();
    Transaction transaction = session.beginTransaction();
    User user = new User();

    user.setName("陆小凤");
    user.setSex("女");
    user.setAddress("湖北武汉");
    session.save(user);
    session.save(user);

    transaction.commit();
    session.close();
}


/**
 *     查询
 *     通过主键来查询
 *
 *     查询不需要开启事务
 */
@Test
public void testGet(){
    Session session = HibernateUtils.getSession();
    User user = session.get(User.class, 7L);
    System.out.println(user);
    session.close();
}

/**
 *     删除流程
 *     先查询出对象，再进行删除
 */
@Test
public void testDelete(){
    Session session = HibernateUtils.getSession();
    Transaction transaction = session.beginTransaction();
    User user = session.get(User.class, 7L);
    session.delete(user);

    transaction.commit();
    session.close();
}


/**
 *     修改对象
 *     先查询出对象，再进行修改
 */
@Test
public void testUpate(){
    Session session = HibernateUtils.getSession();
    Transaction transaction = session.beginTransaction();
    User user = session.get(User.class, 15L);
    if(user != null){
        user.setName("东方不败");
    }
    session.update(user);

    transaction.commit();
    session.close();
}



/**
 *     查询或者修改，如果不存在就插入，存在就修改
 *     不存在的情况下不能主动设置主键，会报错
 */
@Test
public void testSaveOrUpate(){
    Session session = HibernateUtils.getSession();
    Transaction transaction = session.beginTransaction();
    User user = session.get(User.class, 16L);
    user.setName("曹焱兵");
    //主键已经交给了 hibernate 进行管理了，自己设置会报错
//        user.setUid(20L);
    session.saveOrUpdate(user);
    transaction.commit();
    session.close();
}
```

利用 `HQL` 语句查询全部对象

```
/**
 * HQL 语句的查询
 */
@Test
public void testSel(){
    Session session = HibernateUtils.getSession();

    //创建查询的接口
    Query<User> userQuery = session.createQuery("from User", User.class);

    //查询所有的接口
    List<User> list = userQuery.list();

    for (User user :
            list) {
        System.out.println(user);
    }
}

```

关于 `hibernate` 的查询方式有很多，分一篇笔记单独来讲解。
