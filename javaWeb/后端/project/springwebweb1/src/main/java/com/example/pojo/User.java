package com.example.pojo;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// 存储列表的每一行
@Data//这个标注自动为类所有属性创造get/set方法。由lombok提供
@NoArgsConstructor//无参数构造，即构造时无参数传入
@AllArgsConstructor//全参数构造，即构造时传入每一个属性的参数
public class User {
  private Integer id;
  private String username;
  private String password;
  private String name;
  private Integer age;
  private LocalDateTime upDateTime;
  
}
