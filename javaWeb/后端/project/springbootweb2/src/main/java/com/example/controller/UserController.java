package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.service.impl.UserServiceGetData;

@RestController
public class UserController {
  private UserService service = new UserServiceGetData();

  @RequestMapping("/list")
  public List<User> retList(){
    // 这里直接返回一个类实例列表
    // 正常是要返回一个json字符串，但是RequestMapping会自动解析
    List<User> listUser = this.service.getListUser();
    return listUser;
  }
}
