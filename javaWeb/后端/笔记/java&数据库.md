## JDBC
操作关系型数据库的一套API
具体实现由数据库厂商实现
### 1.依赖
- 先启动一个普通maven项目
- 导入依赖
  ```xml
  <dependencies>
      <dependency>
          <groupId>com.mysql</groupId>
          <artifactId>mysql-connector-j</artifactId>
          <version>8.0.33</version>
      </dependency>

      <dependency>
          <groupId>org.junit.jupiter</groupId>
          <artifactId>junit-jupiter</artifactId>
          <version>5.9.3</version>
          <scope>test</scope>
      </dependency>

      <dependency>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok</artifactId>
          <version>1.18.30</version>
      </dependency>
  </dependencies>
  ```
### 2.连接与使用UPDATE的SQL语句
- 示例
  见project里的db01里的test
  ```java
  //1.注册驱动
  Class.forName("com.mysql.cj.jdbc.Driver");//
  //2.获取数据库连接
  String url = "jdbc:mysql://localhost:3306/jdbc01";//`jdbc:mysql:` 就是 JDBC 规定的、专门用来识别 MySQL 数据库的标记头。
  String username = "root";
  String password = "123456";
  Connection connection = DriverManager.getConnection(url,username,password);
  
  //3.获取SQL语句执行对象
  Statement statement = connection.createStatement();
  
  //执行SQL，返回影响的记录数
  int count = statement.executeUpdate("update user set age = 25 where id = 1");
  System.out.println("SQL执行完毕影响的记录数:"+ count);
  
  //释放资源
  statement.close();
  connection.close(); 
  ```
- 说明
  - `Class.forName(全类名)`
    Java 反射方法，作用：**把这个类加载到 JVM，执行这个类的静态代码块**。
### 3.查询语句,连接->执行->结果
- Connection
  ```java
  Connection conn = DriverManager.getConnection(url,username,password);
  ```
- Statement
  - PreparedStatement
    ```java
    String sql = "";
    PreparedStatement stmt = conn.prepareStatement(sql);
    ```
    Statement的升级版

- ResultSet
  ```java
  ResultSet rs = stmt.executeQuery();
  ```
  - 指向
    实例里有方法next();
    效果是从当前行下移动一行,并判断当前行是否为有效行,否则返回false
    刚开始rs指向第一行之前,执行一次next(),当前rs指向第一行
  - 获取结果
    实例有方法getInt(),getString()一类的方法
- 示例:
  ```java
  @Test 
  public void testQuery() throws Exception{
    //1.注册驱动
    Class.forName("com.mysql.cj.jdbc.Driver");
    //2.获取数据库连接
    String url = "jdbc:mysql://localhost:3306/jdbc01";//`jdbc:mysql:` 就是 JDBC 规定的、专门用来识别 MySQL 数据库的标记头。
    String username = "root";
    String password = "123456";
    Connection conn = DriverManager.getConnection(url,username,password);
    
    //3.获取SQL语句执行对象
    String sql = "SELECT id,username,password,name,age FROM user WHERE username = ? AND password = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    
    stmt.setString(1,"daqiao");
    stmt.setString(2,"123456");

    //4.执行语句并获得返回对象
    ResultSet rs = stmt.executeQuery();
    while(rs.next()){
      User user = new User(
        rs.getInt("id"),
        rs.getString("username"),
        rs.getString("password"),
        rs.getString("name"),
        rs.getInt("age")
      );
      System.out.println(user);
    }
    
    //释放资源
    rs.close();
    stmt.close();
    conn.close(); 

  }
  ```
  - 说明
    用setString方法来对?占位符进行操作,对模板操作,防止sql注入.具体其实是sql语句使用预编译,然后把后面两个参数作为参数传入
    调用一次next()后才指向第一行
    资源释放从下到上
## MyBatis
### 1.导入依赖
```xml
		<dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>

		<dependency>
				<groupId>org.mybatis.spring.boot</groupId>
				<artifactId>mybatis-spring-boot-starter</artifactId>
				<version>4.0.1</version>
		</dependency>
```
### 2.配置连接
- 位置
  main\resource\application.properties
- 配置
  新增这些,配置好地址,驱动,账号密码就好
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/jdbc01
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.username=root
  spring.datasource.password=123456
  ```

### 3.操作数据库
- 在类上使用@Mapper注解
- 查询
  在类方法上使用@Select()注解
- 示例
  见mybatis01
  ```java
  @Mapper 
  public interface UserMapper{

    @Select("SELECT * FROM user")
    public List<User> findAll();
  }
  ```
  应用程序在运行时，会自动的为该**接口**创建一个实现类对象（代理对象），并且会自动将该实现类对象存入IOC容器 
  调用findAll(),就是会执行那个SELECT语句并返回一个List<User>对象
  ```java
  @Autowired 
	private UserMapper userMapper;

	@Test
	public void testFindAll() {
		List<User> userList = userMapper.findAll();
		userList.forEach(user->System.out.println(user));
		
	}
  ```
  然后就可以使用了
  