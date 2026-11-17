package com.example.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class RestController {
  @RequestMapping("/list")
  public ArrayList<> retList(){
    // 这里直接返回一个类实例列表

  }
}
