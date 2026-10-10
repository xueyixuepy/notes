# initializer_list用法
#### 形式
  `std::initializer_list<T>` 是 C++11 引入的**轻量级模板类**，专门用来表示**花括号初始化列表 `{ ... }`**。
  如`{1,2}`，在很多场景下会被编译器隐式转成 `initializer_list<int>`
#### 说明
  开销极小
  {}不一定是initializer_list