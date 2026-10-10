# vector用法
#### 插入元素
  `push`传入**已经构造好的对象**，拷贝 / 移动入队
  `emplace`传入**构造参数**，在队列内部原地构造，减少拷贝
  - 例子：
    queue<vector<int>> q;
    ```cpp
    //下两种形式等价，都是先构造临时vector
    q.push({a,b});
    q.emplace( vector<int>{a,b} );
    //这种形式，{a,b}会先生成一个initializer_list<int>轻量视图,交给emplace，在队列内调用vector(initializer_list<int>)，原地构造。更快。
    q.emplace({a,b});//这个可能报错，不要用
    //对于这个，这里是调用vector<int>的构造函数 vector(size_t count, const int& value)这个函数，效果是构造有a个b元素的列表，也是原地构建
    q.emplace(a,b);
    ```
  - 解析：
    - push做了什么
      `q.push({1,2});`
      1. `{1,2}` → 在**函数调用栈**上创建临时 `pair<int,int>(1,2)`
      2. `push` 拿到这个临时右值，调用 `pair` 的**移动构造函数**，在 queue 底层容器（默认是 deque）的内存里，**接管临时对象内部的数据**（这里 pair 很简单，就是两个 int，直接搬值）
      3. **语句执行完毕（分号；）** → 栈上的临时对象生命周期结束，**自动调用析构函数销毁**
      4. queue 里面现在有独立的 pair 元素，不受临时对象销毁影响。


