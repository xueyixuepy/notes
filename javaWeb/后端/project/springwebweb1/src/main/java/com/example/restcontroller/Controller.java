package com.example.restcontroller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.pojo.User;

import cn.hutool.core.io.IoUtil;
@RestController
public class Controller {
  @RequestMapping("/list")
  public List<User> retList(){
    // 这里直接返回一个类实例列表
    // 正常是要返回一个json字符串，但是RequestMapping会自动解析
    InputStream in = this.getClass().getClassLoader().getResourceAsStream("user.txt");
    ArrayList<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8,new ArrayList<>());
    List<User> ret= lines.stream().map(line->{
      String[] datas = line.split(",");
      return new User(Integer.parseInt(datas[0]),datas[1],datas[2],datas[3],Integer.parseInt(datas[4]),LocalDateTime.parse(datas[5],DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }).collect(Collectors.toList());
    return ret;
  }
}
