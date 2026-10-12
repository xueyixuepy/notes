### 在此记录c++的部分函数
##### 1.std::sort()
- 头文件:`<algorithm>`
- 参数:`sort(起点迭代器, 终点迭代器, [可选比较函数])`
- 默认升序,可使用`greater<数据类型>()`比较函数进行降序.
- lambal表达式比较
  ```cpp
  vector<int> v = {5,2,9,1};
  sort(v.begin(), v.end(), [](int a, int b){
      return a > b; // 降序
  });
  ```
  升序：return a < b
  降序：return a > b
  