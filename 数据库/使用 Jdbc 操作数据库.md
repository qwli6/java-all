# 使用 jdbc 完成数据库的查询操作


**数据库脚本**

```
create table category (
  cid VARCHAR(20) PRIMARY KEY,
  cname VARCHAR(20)
);

insert into category VALUE ('c001', '电器');
insert into category VALUE ('c002', '化妆品');
insert into category VALUE ('c003', '书籍');
insert into category VALUE ('c004', '手机');
insert into category VALUE ('c005', '衣服');


```

添加 `mysql` 驱动包的依赖

```
compile group: 'mysql', name: 'mysql-connector-java', version: '6.0.6'
```

> 基本步骤
> 
> 1. 加载驱动
> 
> 2. 获取连接
> 
> 3. 编写 sql
> 
> 4. 创建语句执行者 PrepareStatement
> 
> 5. 设置参数
> 
> 6. 执行 sql
> 
> 7. 处理结果
> 
> 8. 释放资源


----

# 1. 查询数据库全部数据

```
@Test
public void test() throws Exception {


    //加载数据库驱动
    Class.forName("com.mysql.jdbc.Driver");
    //获取连接
    Connection connection = DriverManager.
            getConnection("jdbc:mysql://localhost:3306/jdbc","root", "student");

    //编写sql
    String sql = "select * from category";

    //创建语句执行者
    PreparedStatement statement = connection.prepareStatement(sql);

    //设置参数

    //执行 sql
    ResultSet resultSet = statement.executeQuery();

    //处理结果
    while (resultSet.next()){
        String id = resultSet.getString("cid");
        String name = resultSet.getString("cname");

        System.out.println("id = "+id + "== name " + name);
    }
  	//释放资源
    resultSet.close();
    statement.close();
    connection.close();

}

```

**获取连接部分**

```
Connection connection = DriverManager.
            getConnection("jdbc:mysql://localhost:3306/jdbc","root", "student");
```
**参数 1：告诉我们连接什么类型的数据库以及连接哪个数据库**

`格式：  协议：数据库类型：子协议 参数`

> mysql : jdbc:mysql://localhost:3306/数据库名称
> 
> oracle : jdbc:oracle:thin@localhost:1232@实例
> 
> 

**参数 2 ： 账户名**

**参数3 ： 密码**


----


# 2. 执行插入


```
/**
 * int executeUpdate()
 * 执行 cud 语句（增删改）
 * @throws Exception
 */
@Test
public void testInsert() throws Exception {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    connection = JdbcUtils.getConn();

    String sql = "insert into category(cid, cname) values(?, ?)";

    preparedStatement = connection.prepareStatement(sql);
    //设置参数
    preparedStatement.setString(1, "c006");
    preparedStatement.setString(2, "电脑");

    int i = preparedStatement.executeUpdate();
    if(i == 1){
        System.out.println("成功");
    }else{
        System.out.println("失败");
    }

    JdbcUtils.releaseResource(connection, preparedStatement, null);
}
```

封装的工具类 `jdbcUtils.java`

```
public class JdbcUtils2 {

    private static final String DRIVER_URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final String DRIVER_CLASS;



	 //还可以利用 Properties 来操作
	 //Properties properties = new Properties();
    //properties.load(new FileInputStream("src/main/resources/jdbc.properties"));
    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
        USERNAME = resourceBundle.getString("username");
        PASSWORD = resourceBundle.getString("password");
        DRIVER_URL = resourceBundle.getString("url");
        DRIVER_CLASS = resourceBundle.getString("driver_class");
    }

    /**
     * 获取连接
     * @return
     */
    public static Connection getConn() throws Exception {

        //加载数据库驱动
        Class.forName(DRIVER_CLASS);
        //获取连接
        return DriverManager.getConnection(DRIVER_URL,USERNAME, PASSWORD);
    }


    public static void releaseResource(Connection conn, Statement st, ResultSet rs){
        closeResult(rs);
        closeStatement(st);
        closeConn(conn);
    }


    public static void closeConn(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    public static void closeStatement(Statement st){
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            st = null;
        }
    }

    public static void closeResult(ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
    }
}

```



`src/resources` 目录下的资源文件

```
driver_class = com.mysql.jdbc.Driver
url = jdbc:mysql://localhost:3306/jdbc
username = root
password = student
```

----

# 3. 修改数据

```
@Test
public void testUpdate(){
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
        connection = JdbcUtils2.getConn();
        String sql = "update category set cname = ? where cid = ?";
        preparedStatement = connection.prepareStatement(sql);

		 //设置参数，
        preparedStatement.setString(1, "xx");
        preparedStatement.setString(2, "c006");

        int i = preparedStatement.executeUpdate();
        if (i == 1){
            System.out.println("success");
        }else{
            System.out.println("failed");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }finally {
        JdbcUtils2.releaseResource(connection, null, null);
    }
}
```

# 4. 删除语句

```
@Test
public void testDelete(){
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
        connection = JdbcUtils2.getConn();
        String sql = "delete from category where cid = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, "c006");

        int i = preparedStatement.executeUpdate();
        if (i == 1){
            System.out.println("success");
        }else{
            System.out.println("failed");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }finally {
        JdbcUtils2.releaseResource(connection, null, null);
    }
}
```



> **注意**
> 
> 1. 一般执行增删改的时候调用的是 executeUpdate() 方法，这个方法的返回值是 int 类型，表示执行完成这条语句后，对数据库数据影响的行数
> 
> 2. executeQuery() 方法是用来查询数据的，返回值是 ResultSet
