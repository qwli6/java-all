### `hibernate` 实现一对多映射关系

前面所说的都是关于单表的操作，下面说一下 `hibernate` 中关于多表的操作。拿用户和订单来举例子，一个用户可以对应多个订单，一个订单对应一个用户，那么用户和订单之间的关系就是 `一对多` 的关系。`hibernate` 操作一对多需要用到 `set` 和 `many-to-one` 标签。

- `hibernate.cfg.xml` 中的配置

`hibernate.cfg.xml` 的配置我们已经写过很多次了，这里就不再多做解释了。无非就是分为：

1.  `常用配置`
2. `可选配置` 
3. 以及引入 `xxx.hbm.xml` 映射文件。

```xml
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <!-- 一个数据库对应一个 sessionfactory-->
    <session-factory>
        <!--必须配置的几个参数
            1. url
            2. driver_class
            3. username //数据库用户名
            4. password //数据库密码
            5. dialect //数据库方言
        -->
        <property name="hibernate.connection.url">jdbc:mysql:///hibernate_day03</property>
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">student</property>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>
        <!--
            可选的一些参数
            1. 是否显示执行的 sql 语句
            2. 是否利用 hibernate 提供的自动建表功能
            3. 是否格式化 sql 语句
        -->
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="hibernate.format_sql">true</property>
        <!--
            引入 mapping 映射文件
        -->
        <mapping resource="hbm/User2.hbm.xml"/>
        <mapping resource="hbm/Order.hbm.xml"/>
    </session-factory>
</hibernate-configuration>
```

- 分析一对多的过程

用户和订单表：外键设置在订单表中，订单中的外键值由用户表中得来，例如如下的分析。

> 一个用户有多个订单，一个订单对应一个用户，因此用户和订单是一对多的关系。
>
> 用户           订单
>
> uid           oid
>
> name          orderName
>
> address       uid  //外键，值由 user 表而来

-  编写实体类

用户表 `User2.java`

```java
/**
 * 用户表
 * 一个用户对应多个订单
 */
public class User2 {

    private Integer id;
    private String name;
    private Integer age;
    private String address;

	/**
	 * hibernate 中只有一个 Set 集合，一般配置在一方，
	 * 并且必须要手动初始化，不手动初始化会报错
    **/
    private Set<Order> orderSet = new HashSet<Order>();

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

```

订单表 `Order.java`

```java
public class Order {

    private String oid;
    private String goodName;
    private String content;
    private String address;

	/**
	* 配置一个用户，关联查询出来的用户数据都会封装在该对象中。
	* 不需要手动初始化，手动初始化会报错。
    **/
    private User2 user;

    public void setUser(User2 user) {
        this.user = user;
    }

    public User2 getUser() {
        return user;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

```

- 映射文件 `User2.hbm.xml` 以及 `Order.hbm.xml`

在 `set` 子标签中配置级联删除和级联更新。

用户 `User2.hbm.xml`

```xml
<hibernate-mapping package="com.itqiwen.hibernate.domain">
    <class name="User2" table="user2">
        <id name="id" column="id">
            <generator class="native"/>
        </id>

        <property name="name" column="name"/>
        <property name="address" column="address"/>
        <property name="age" column="age"/>

        <!-- 
        	一方，配置 set
        	name 为 User 类中的 Set 集合的属性名称
        	key 表示哪一个属性作为外键
        	One-to-many 中的 class 属性表示属性映射到哪一个类中去
        	cascade 表示配置了级联可以配置级联删除和级联更新。
        	一般不要配置级联删除，容易出现误操作。
        -->
        <set name="orderSet" cascade="save-update, delete">
            <key column="id"/>
            <one-to-many class="Order"/>
        </set>
    </class>
</hibernate-mapping>
```

在 `many-to-one` 子标签中配置级联删除和级联更新。

订单 `Order.hbm.xml`

```xml
<hibernate-mapping package="com.itqiwen.hibernate.domain">
    <!-- order 是关键字，使用 order 会导致建表不成功 -->
    <class name="Order" table="order1">
        <id name="oid" column="oid">
            <generator class="uuid"/>
        </id>

        <property name="goodName" column="good_name"/>
        <property name="address" column="good_address"/>
        <property name="content" column="good_content"/>

        <!-- 
        级联添加和级联删除
        name 是 Order 类中的成员属性名称
        column 表示数据库中的 Order1 表中的外键字段是哪一个
        class 表示查询出来的结果映射到哪一个类中
        cascade 配置级联删除和级联更新
        -->
        <many-to-one name="user" column="id" class="User2" cascade="save-update, delete"/>
    </class>

</hibernate-mapping>
```

- 测试代码

在不配置级联删除和级联更新的前提下，我们使用双向关联的方式来保存数据，如下便是双向关联的测试代码，都不配置的话，需要自己手动去关联双方。

```java
/**
 * 测试双向关联
 * 并没有在 hbm.xml 文件中配置级联属性
 */
@Test
public void run(){
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();

    User2 user2 = new User2();
    user2.setName("曹焱兵");
    user2.setAddress("罗刹街");
    user2.setAge(16);

    Order o1 = new Order();
    o1.setAddress("罗刹街");
    o1.setContent("外卖一号");

    Order o2 = new Order();
    o2.setAddress("罗刹街");
    o2.setContent("外卖二号");

    //订单和用户关联
    o1.setUser(user2);
    o2.setUser(user2);

    //用户和订单关联
    user2.getOrderSet().add(o1);
    user2.getOrderSet().add(o2);

    session.save(user2);
    session.save(o1);
    session.save(o2);

    transaction.commit();
    session.close();
}
```

在一方配置级联删除和级联更新的情况下，需要手动去关联另一方。

```java
/**
 * 测试级联保存
 * 在 hbm.xml 文件中配置级联属性
 *
 * 只在用户中配置，也就是单向级联
 * cascade="save-update"
 */
@Test
public void run2(){
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();


    User2 user2 = new User2();
    user2.setName("曹焱亮");
    user2.setAddress("死魂岛");
    user2.setAge(19);

    Order o1 = new Order();
    o1.setAddress("死魂岛");
    o1.setContent("曹家霸器");

    Order o2 = new Order();
    o2.setAddress("死魂岛");
    o2.setContent("武神躯");

    //用户关联订单
    user2.getOrderSet().add(o1);
    user2.getOrderSet().add(o2);

    /**
     * 在用户中配置了级联属性，所以在保存用户的时候，会级联保存订单
     */
    session.save(user2);
    transaction.commit();
}
```

用户和订单都同时配置级联删除和级联更新的时候，操作便变得容易的多。

```java
/**
 * 双向级联
 * User 和 Order 之间都配置了 cascade = save-update 属性，也就是说
 * 插入用户时会级联插入 order
 * 插入 order 会级联插入 user
 */
@Test
public void run3(){
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();

    User2 user2 = new User2();
    user2.setName("曹焱亮2");
    user2.setAddress("死魂岛2");
    user2.setAge(29);
    
    Order o1 = new Order();
    o1.setAddress("死魂岛2");
    o1.setContent("曹家霸器2");
    Order o2 = new Order();
    o2.setAddress("死魂岛2");
    o2.setContent("武神躯2");

    // 订单1关联用户
    o2.setUser(user2);
    // 用户关联订单2
    user2.getOrderSet().add(o1);

    // 保存订单2  --》级联保存用户 --》级联保存订单1
    session.save(o2);
    transaction.commit();
}
```

以上便是级联更新，下面看看级联删除的代码。

```java
/**
 * 测试级联删除
 */
@Test
public void run4(){
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    User2 user2 = session.get(User2.class, 8);
    if(user2 != null){
        session.delete(user2);
    }
    transaction.commit();
}
```

> 注意： 级联保存需要在 `hbm.xml` 映射文件中添加 `cascade="save-update"`，另外在现实的开发环境中，我们并不配置级联删除，容易出现误操作，删除相关数据。

