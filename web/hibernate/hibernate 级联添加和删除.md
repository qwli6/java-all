# hibernate 实现一对多映射关系


## 1. hibernate.cfg.xml 配置

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>

        <property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>
        <property name="hibernate.connection.url">jdbc:mysql:///hibernate_day03?useSSL=false</property>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">student</property>

        

        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.format_sql">true</property>
        <property name="hibernate.hbm2ddl.auto">update</property>


        <mapping resource="user.hbm.xml"/>
        <mapping resource="order.hbm.xml"/>
        <mapping resource="test.hbm.xml"/>

  
    </session-factory>
</hibernate-configuration>

```


## 2. 分析过程

用户和订单表：外键设置在订单表中。

```
一个用户有多个订单，一个订单对应一个用户，因此用户和订单是一对多的关系。

用户           订单

uid           oid
name          orderName
address       uid  //外键，值由 user 表而来

```

## 3. 编写实体类

**`User.java`**

```
/**
 * 用户表
 * 一个用户对应多个订单
 */
public class User {
    private Long uid;
    private String name;
    private String address;


	//必须要手动初始化，并且 hibernate 中只有一个 Set 集合，不手动初始化会报错
    private Set<Order> orders = new HashSet<>();

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

```

**`Order.java`**

```
package com.lqwit.hibernate.domain;

/**
 * 一个订单对应一个用户
 *
 */
public class Order {
    private Long oid;
    private String orderName;

	//不需要手动初始化，手动初始化会报错
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", orderName='" + orderName + '\'' +
                '}';
    }
}

```

## 4. 映射文件 User.hbm.xml 以及 Order.hbm.xml


**User.hbm.xml**

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="com.lqwit.hibernate.domain.User" table="user">
        <id name="uid" column="uid" >
            <generator class="native"/>
        </id>
        <property name="name" column="name"/>
        <property name="address" column="address"/>

		
        <set name="orders">
            <!-- 表示外键的字段-->
            <key column="uid"/>
            <one-to-many class="com.lqwit.hibernate.domain.Order"/>
        </set>
    </class>
</hibernate-mapping>
```

**Order.hbm.xml**

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <!-- order 是保留字，使用 order 建表会报错-->
    <class name="com.lqwit.hibernate.domain.Order" table="user_order">
        <id name="oid" column="oid" >
            <generator class="native"/>
        </id>
        <property name="orderName" column="name"/>


        <!--
        name  javaBean 中的属性
        class 属性要映射到那个类中
        column  外键字段
        -->
        <many-to-one name="user" class="com.lqwit.hibernate.domain.User"
                     column="uid"/>
    </class>
</hibernate-mapping>
```


## 5. 测试代码

```
public class HibernateTest {


    private Session session;
    private Transaction transaction;

    @Before
    public void setUp(){
        Configuration configure = new Configuration().configure();
        SessionFactory sessionFactory = configure.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        System.out.println("单元测试之前调用");

    }

    /**
     * 双向关联
     */
    @Test
    public void test1(){
        User user = new User();
        user.setName("熊大");

        Order order = new Order();
        order.setOrderName("天猫");

        Order order2 = new Order();
        order2.setOrderName("淘宝");

        user.getOrders().add(order);
        user.getOrders().add(order2);

        order.setUser(user);
        order2.setUser(user);

        session.save(user);
        session.save(order);
        session.save(order2);
    }

    /**
     * 测试级联添加
     */
    @Test
    public void test2(){
        User user = new User();
        user.setName("熊二");

        Order order = new Order();
        order.setOrderName("天猫");
        user.getOrders().add(order);

        Order order1 = new Order();
        order1.setOrderName("淘宝");
        order1.setUser(user);

    }
}


//output

Hibernate: 
    
    create table user (
       uid bigint not null auto_increment,
        name varchar(255),
        address varchar(255),
        primary key (uid)
    ) engine=MyISAM
Hibernate: 
    
    create table user_order (
       oid bigint not null auto_increment,
        name varchar(255),
        uid bigint,
        primary key (oid)
    ) engine=MyISAM
Hibernate: 
    
    alter table user_order 
       add constraint FKs8mgqp6lnqannqv55kc45jdc9 
       foreign key (uid) 
       references user (uid)
单元测试之前调用
Hibernate: 
    insert 
    into
        user
        (name, address) 
    values
        (?, ?)
Hibernate: 
    insert 
    into
        user_order
        (name, uid) 
    values
        (?, ?)
Hibernate: 
    insert 
    into
        user_order
        (name, uid) 
    values
        (?, ?)
```


> 注意： 级联保存需要在 `hbm.xml` 映射文件中添加 `cascade="save-update"`

