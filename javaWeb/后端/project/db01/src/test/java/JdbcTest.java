import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

import com.example.pojo.User;

import com.example.pojo.User;
public class JdbcTest {
  @Test 
  public void testUpdate() throws Exception{
    //1.注册驱动
    Class.forName("com.mysql.cj.jdbc.Driver");
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

  }
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

    //执行语句并获得返回对象
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
}
