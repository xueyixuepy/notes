# c++的string类型笔记
#### substr()
- string s;
  s.substr(pos,num);
  表示分割从下标pos开始以及后面共num个元素。
  不写num表示从下标pos开始的所有元素。
  如果pos恰好等于s.size()，也有返回，但是空。
  pos不能超过s.size()