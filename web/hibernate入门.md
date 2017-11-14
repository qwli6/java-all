# hibernate 入门

hibernate 是一个经典的 orm（对象关系映射）框架



# 1. 入门

利用 idea 新建一个 web 工程，选中 hibernate 模块，让 idea 自动去帮我们下载所需要的 jar 包。此处可能会下载失败，如果下载失败了，换下网络重新下载即可。


# 2. 编写对象映射文件以及实体类

```
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="com.lqwit.domain.User" table="user">
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

# 编写核心配置文件 hibernate.cfg.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <!-- 一个数据库值对应一个 session-factory-->
    <session-factory>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="hibernate.connection.url">jdbc:mysql:///mybatis01</property>
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="hibernate.connection.password">student</property>
        <property name="hibernate.connection.username">root</property>


        <property name="hibernate.show_sql">true</property>
        <!--<property name="hbm2ddl.auto">update</property>-->



        <mapping resource="com/lqwit/domain/User.hbm.xml"/>
    </session-factory>


</hibernate-configuration>
```


# 测试

```
@Test
    public void textSave(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");


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

# 输出结果

```
Hibernate: insert into user (name, sex, address) values (?, ?, ?)
```