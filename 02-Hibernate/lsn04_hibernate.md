# `hibernate` 几种查询方式

### 1. 唯一标识 OID 的查询方式
这种方式最简单，也就是通过主键来查询

```
@Test
public void run(){
    Session session = HibernateUtil.getSession();
    User user = session.get(User.class, 1);
    System.out.println(user);
}
```

`get` 方式是查询对象时候，没有进行懒加载的查询方式，`load` 方法是查询的时候使用了懒加载的方式。


### 2. 对象导航方式

```
@Test
public void run2(){
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    //现有 OID 的查询方式
    User2 user2 = session.get(User2.class, 4);

    System.out.println("===================");
    //查询该用户下的订单集合
    //直接从 user 中拿出 OrderSet 就可以查询出来
    Set<Order> orderSet = user2.getOrderSet();
    System.out.println(user2.getOrderSet().size());
    transaction.commit();
}
```

拿用户和订单的例子来说明，通过 `唯一标识` 的方式查询出我们所需要的用户对象，然后通过查询出来的用户获取该用户下的订单状态就是 `对象导航` 的方式。

### 3. `HQL` 查询
`HQL` 查询全称就是 `hibernate query language`，也就是 `Hibernate` 提供的一种查询方式。

```
@Test
public void run3(){
    Session session = HibernateUtil.getSession();
    Query query = session.createQuery("from User");
    List<User> list = query.list();
    for (User user :
            list) {
        System.out.println(user);
    }
}
```

> 注意：HQL 语句中没有 `*` 

同样，和执行 `sql` 语句一样，可以给对象起别名（数据库是给表起别名）。

```
@Test
public void run31(){
    Session session = HibernateUtil.getSession();
//        List<User2> list = session.createQuery("from User2 as u").list();//as 可以省略
    List<User2> list = session.createQuery("select u from User2 as u").list();//as 可以省略
    for (User2 user2 :
            list) {
        System.out.println(user2);
    }
}
```

还可以进行排序

```
@Test
public void run32(){
    Session session = HibernateUtil.getSession();
    List<User2> list = session.createQuery("select u from User2 as u order by id desc").list();
    for (User2 user2 :
            list) {
        System.out.println(user2);
    }
}

```

同样，`hibernate` 给我们提供了分页的方法。

```
@Test
public void run33(){
    Session session = HibernateUtil.getSession();
    Query query = session.createQuery("select u from User2 as u order by id desc");
    query.setFirstResult(0);//从哪天记录开始，默认从 0 开始
//        query.setFirstResult((2-1)*3);//查询第二页的数据
    query.setMaxResults(3);//每一页显示多少数据
    List<User2> list = query.list();
    for (User2 user2 :
            list) {
        System.out.println(user2);
    }
}

```

当然，如果我们要执行条件查询，也可以添加查询条件

```
/**
 * HQL
 * 利用 setParameter 查询添加条件
 */
@Test
public void run4(){
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    Query query = session.createQuery("from User2 where age > ?");
    query.setParameter(0, 13);

    List<User2> list = query.list();
    for (User2 user :
            list) {
        System.out.println(user);
    }
    transaction.commit();
    session.close();
}

/**
 * HQL 添加查询条件
 */
@Test
public void run5(){
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    Query query = session.createQuery("from User2 where age >:age");
//        query.setParameter(age, 13);

    query.setParameter("age", 13);
    List<User2> list = query.list();
    for (User2 user :
            list) {
        System.out.println(user);
    }
    session.close();
}
```

或者进行模糊查询

```
/**
 * HQL 模糊查询
 */
@Test
public void run51(){
    Session session = HibernateUtil.getSession();
    List<User2> list = session.createQuery("from User2 where name like ?")
            .setParameter(0, "%曹%").list();
    for (User2 user :
            list) {
        System.out.println(user);
    }

}
```

### 4. 投影查询

以上的查询方式都是查询的全部字段，同样和执行 `sql` 一样，我们可以指定查询某一些字段。

```
/**
 * 投影查询，可以不查全部字段，并且指定查询
 * 某一些字段
 * //output
 * [小王, 12]
 * [小王2, 22]
 * [张三, 23]
 * [曹焱兵, 16]
 * [曹焱亮, 19]
 */
@Test
public void run6(){
    Session session = HibernateUtil.getSession();
    //只查询用户表中的 name 字段
    List<Object[]> list = session.createQuery("select u.name, u.age from User2 u").list();
    for (Object[] objs : list) {
        System.out.println(Arrays.toString(objs));
    }
}
```

当然以上的字段都是封装成数组，我们也可以将我们要查询的字段封装成对象。

```
/**
 * 把投影查询出来的结果映射到对象中
 * //output
 * User2{id=null, name='小王', age=12, address='null'}
 * User2{id=null, name='小王2', age=22, address='null'}
 * User2{id=null, name='张三', age=23, address='null'}
 * User2{id=null, name='曹焱兵', age=16, address='null'}
 * User2{id=null, name='曹焱亮', age=19, address='null'}
 *
 */
@Test
public void run61(){
    Session session = HibernateUtil.getSession();
    //在 User2 中提供要查询字段的构造方法
    Query query = session.createQuery("select new User2(u.name, u.age) from User2 u");
    List<User2> list = query.list();
    for (User2 user2 : list) {
        System.out.println(user2);
    }
}


//在 User.java 中需要提供指定查询字段的构造方法

public User2(String name, Integer age) {
    this.name = name;
    this.age = age;
}

```

另外，我们通常也可以执行聚合函数，比如 `count(), sum(), avg(), max(), min()` 等等。

```
 /**
 * 聚合函数： sum() count() avg() max() min()
 */
@Test
public void run7(){
    Session session = HibernateUtil.getSession();
    //查询所有的联系人的数量
//        List<Number> list = session.createQuery("select count(*) from  User2 ").list();
    List<Number> list = session.createQuery("select count(u) from  User2 u ").list();
    //通过下标取值
    int i = list.get(0).intValue();
    System.out.println(i);
}

```

### 5. `QBC` 查询方式

所谓 `QBC` 查询，就是 `Query By Criteria`，也就是条件查询，`QBC` 查询是最适合做条件查询的。

> 注意：在 `hibernate 5.2 `版本之后，`Criteria` 查询方式已经被标记为废弃，官方推荐我们使用 `JPA Criteria` 来执行条件查询。

```
/**
 * QBC 查询方式 Query By Criteria （按条件查询）
 */
@Test
public void run8(){
    Session session = HibernateUtil.getSession();

    //创建 QBC 的查询接口
    Criteria criteria = session.createCriteria(User2.class);
    criteria.add(Restrictions.gt("age", 14));

    List<User2> list = criteria.list();
    for (User2 user : list) {
        System.out.println(user);
    }
}

```

`QBC` 升序，降续

```
/**
 * QBC 按id升序
 */
@Test
public void run9(){
    Session session = HibernateUtil.getSession();
    Criteria criteria = session.createCriteria(User2.class);
    //升序
    criteria.addOrder(org.hibernate.criterion.Order.asc("id"));
//        criteria.addOrder(org.hibernate.criterion.Order.desc("id"));
    List<User2> list = criteria.list();
    for (User2 user : list) {
        System.out.println(user);
    }
}

```

`QBC` 分页查询

```
/**
 * QBC 分页
 */
@Test
public void run10(){
    Session session = HibernateUtil.getSession();
    Criteria criteria = session.createCriteria(User2.class);
    //升序
    criteria.addOrder(org.hibernate.criterion.Order.desc("id"));
    criteria.setFirstResult(0);
    criteria.setMaxResults(3);
    List<User2> list = criteria.list();
    for (User2 user : list) {
        System.out.println(user);
    }
}

```

`QBC` 条件查询

```
/**
 * QBC 条件查询
 */
@Test
public void run11(){
    Session session = HibernateUtil.getSession();
    Criteria criteria = session.createCriteria(User2.class);
    /**
     * 使用 Restrictions 添加条件
     */
    criteria.add(Restrictions.like("name", "%曹%"));
    List<User2> list = criteria.list();
    for (User2 user : list) {
        System.out.println(user);
    }
}


/**
 * QBC or 方法查询
 */
@Test
public void run12(){
    Session session = HibernateUtil.getSession();
    Criteria criteria = session.createCriteria(User2.class);
    /**
     * 使用 Restrictions 添加条件
     * sql  select * from user2 where age > 13 or name like %曹%;
     */
    criteria.add(Restrictions.or(Restrictions.gt("age", 13),
            Restrictions.like("name", "%曹%")));

    List<User2> list = criteria.list();
    for (User2 user : list) {
        System.out.println(user);
    }
}

```

`QBC` 查询聚合函数

```
/**
 * QBC 查询聚合条件
 * Projections: 聚合函数的工具类
 */
@Test
public void run13(){
    Session session = HibernateUtil.getSession();
    Criteria criteria = session.createCriteria(User2.class);
    List<Number> id = criteria.setProjection(Projections.count("id")).list();
    long l = id.get(0).longValue();
    System.out.println(l);

    //重置聚合函数条件
    criteria.setProjection(null);
    List<User2> list = criteria.list();
    for (User2 user :
            list) {
        System.out.println(user);
    }

}
```

#### 离线条件查询
所谓离线条件查询，就是指查询条件不依赖 `Session`，只有当真正查询的时候才依赖 `Session`。

```
/**
 * 离线条件查询
 * DetachedCriteria 创建不依赖 session，查询需要依赖 session
 */
@Test
public void run14(){

    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User2.class);
    detachedCriteria.add(Restrictions.like("name", "%曹%"));

    Criteria criteria = detachedCriteria.getExecutableCriteria(HibernateUtil.getSession());
    List<User2> list = criteria.list();
    for (User2 user :
            list) {
        System.out.println(user);
    }
}

```

### 6. 执行原生的 `sql` 查询
当以上的条件不满足条件的时候，我们可以考虑使用原生的 `sql` 来执行查询。当然这种查询方式会破坏 `hibernate` 原有的 `orm` 关系。

将查询的结果封装到 `Object[]` 中去。

```
/**
 * 执行原生SQL语句查询
 * 默认是封装到数组中的，我们可以执行添加实体对象选择将数据封装到对象中--具体实现 run16中
 */
@Test
public void run15(){
    Session session = HibernateUtil.getSession();
//        NativeQuery sqlQuery = session.createSQLQuery("select * from user2");
    NativeQuery query = session.createNativeQuery("select * from user2");
    List<Object[]> list = query.list();
    for (Object[] objs :
            list) {
        System.out.println(Arrays.toString(objs));
    }
}
```

同样我们也可以把查询出来的结果封装到实体类中去。

```
/**
 * 原生 sql 查询，封装查询结果到实体对象zhogn
 */
@Test
public void run16(){
    Session session = HibernateUtil.getSession();
    List<User2> list = session.createNativeQuery("select * from user2").addEntity(User2.class).list();
    for (User2 user :
            list) {
        System.out.println(user);
    }
}
```

# 总结
以上就是 `hibernate` 给我们提供的集中查询方式，当然仅仅只是针对单表查询。但是以上的几种方式足以让我们能够应付大部分的查询。

