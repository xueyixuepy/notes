package com.example.service.impl;
import com.example.service.UserService;


import com.example.dao.UserDao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.example.dao.impl.UserDaoGetData;
import com.example.pojo.User;
public class UserServiceGetData implements UserService{
  private UserDao lines = new UserDaoGetData();

  @Override 
  public List<User> getListUser(){
    List<String> datalines= this.lines.getUserDataLines();
    List<User> ret= datalines.stream().map(line->{
      String[] datas = line.split(",");
      return new User(Integer.parseInt(datas[0]),datas[1],datas[2],datas[3],Integer.parseInt(datas[4]),LocalDateTime.parse(datas[5],DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }).collect(Collectors.toList());
    return ret;
  }
}
