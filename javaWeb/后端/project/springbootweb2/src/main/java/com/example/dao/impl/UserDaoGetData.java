package com.example.dao.impl;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import cn.hutool.core.io.IoUtil;
import com.example.dao.UserDao;

public class UserDaoGetData implements UserDao {
  @Override 
  public List<String> getUserDataLines(){
    InputStream in = this.getClass().getClassLoader().getResourceAsStream("user.txt");
    ArrayList<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8,new ArrayList<>());
    return lines;
  }
}
