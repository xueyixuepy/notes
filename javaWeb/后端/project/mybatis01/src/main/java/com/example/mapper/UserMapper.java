package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import com.example.pojo.User;
@Mapper 
public interface UserMapper {

  @Select("SELECT * FROM user")
  public List<User> findAll();
}
