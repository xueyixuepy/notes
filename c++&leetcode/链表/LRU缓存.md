# 记录
- 题目:
  请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
  实现 LRUCache 类：
  LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
  int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
  void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
  函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
- 示例:
  ```  
  输入
  ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
  [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
  输出
  [null, null, null, 1, null, -1, null, -1, 3, 4]
  解释
  LRUCache lRUCache = new LRUCache(2);
  lRUCache.put(1, 1); // 缓存是 {1=1}
  lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
  lRUCache.get(1);    // 返回 1
  lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
  lRUCache.get(2);    // 返回 -1 (未找到)
  lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
  lRUCache.get(1);    // 返回 -1 (未找到)
  lRUCache.get(3);    // 返回 3
  lRUCache.get(4);    // 返回 4
  ```
- 思路:
  我的想法是使用两个队列,让一个队列出,进入另一个队列,截取被使用的部分放在接收的队列的最后,但是AI说这个时间复杂度是O(n)而不是O(1),因为每次都要遍历完整的队列.
- 我的解法(超出时间限制了)
```cpp
class LRUCache {
public:
    int capacity;
    queue<vector<int>> q1;
    queue<vector<int>> q2;
    LRUCache(int capacity) {
        this->capacity = capacity;
    }
    
    int get(int key) {
        //tq1为空,tq2非空
        vector<int> tem(2);
        bool found = false;
        int qSize = 0;
        queue<vector<int>>* tq1 = &(this->q1.size()==0?this->q1:this->q2);
        queue<vector<int>>* tq2 = &(tq1 == &this->q1?this->q2:this->q1);
        qSize = tq2->size();
        for(int i = 0;i<qSize;i++){
            if(tq2->front()[0]==key){
                tem = tq2->front();
                tq2->pop();
                found = true;
            }
            else{
                tq1->push(tq2->front());
                tq2->pop();
            }

        }
        if(found){
            tq1->push(tem);
            return tem[1];
        }
        else{
            return -1;
        }
    }
    
    void put(int key, int value) {
        //tq1为空,tq2非空
        vector<int> tem(2);
        bool found = false;
        int qSize = 0;
        queue<vector<int>>* tq1 = &(this->q1.size()==0?this->q1:this->q2);
        queue<vector<int>>* tq2 = &(tq1 == &this->q1?this->q2:this->q1);
        qSize = tq2->size();
        for(int i = 0;i<qSize;i++){
            if(tq2->front()[0]==key){
                tq2->front()[1] = value;
                tem = tq2->front();
                tq2->pop();
                found = true;
            }
            else{
                tq1->push(tq2->front());
                tq2->pop();
            }

        }
        if(found){
            tq1->push(tem);
            return ;
        }
        else{
            if(tq1->size()>=this->capacity){
                tq1->pop();
                tem[0] = key;
                tem[1] = value;
                tq1->push(tem);
            }
            else{
                tem[0] = key;
                tem[1] = value;
                tq1->push(tem);
            }
            return;
        }
    }
};

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache* obj = new LRUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */

```
- 结果:
  是有效的,但不符合规则且,超出时间限制,而且在写的过程中出了很多问题,**要记住**:
  ==对于vector<>这类东西,要去操作它本身,需要去&,取得他的指针,然后对指针用->来操作他的成员.==
  ==然后是对于for循环,里面的条件判断会在每次循环时检查,如果使用了.size()这种函数,它本身又在变小的话,会导致遍历不完整==
  ==如果提前给vector分配好了空间,那么他的size()就不在是0了,不能用.size()>0来判断有没有成员==
- 我再试着做了一遍,因为发现这题属于链表题.
  我的思路是建立双向链表,记录头和尾,快速删去尾部和插入新头
  做的过程中也是bug多多,不过好在最后通过了,虽然时间击败13%,不过内存击败99%.
  ai说我这个方法时间复杂度依旧是O(n),罢了,好歹是做出来了.
  代码如下:
```cpp
class listNode{
public:
    listNode* next = nullptr;
    listNode* before = nullptr;
    int val;
    int key;
    listNode(int key,int val){
        this->key = key;
        this->val = val;
    };
    listNode(int key,int val,listNode* next,listNode* before){
        this->key = key;
        this->val = val;
        this->next = next;
        this->before = before;
    };
};

class LRUCache {
public:
    int capacity;
    listNode* head;
    listNode* tail;
    int listSize = 0;
    LRUCache(int capacity) {
        this->capacity = capacity;
        this->head = nullptr;
        this->tail = nullptr;
    }
    
    int get(int key) {
        listNode* tem =this->head;
        while(tem!=nullptr){
            if(tem->key==key){
                if(tem->before!=nullptr){
                    tem->before->next = tem->next;
                    if(tem->next!=nullptr){
                        tem->next->before=tem->before;
                    }
                    else{//移动尾部需要修改tail
                        this->tail = tem->before;
                    }
                    tem->next=this->head;
                    this->head->before = tem;
                    tem->before = nullptr;
                    this->head = tem;
                }
                return tem->val;
            }
            tem=tem->next;
        }
        return -1;
    }
    
    void put(int key, int value) {
        
        if(this->head == nullptr){//初始化
            this->head = new listNode(key,value);
            this->tail = this->head;
            this->listSize ++;
            return ;
        }
        else{//遍历查找,查到了搬回首位,注意尾部移动需修改tail
            listNode* tem = this->head;
            while(tem!=nullptr){
                if(tem->key == key){
                    if(tem->before!=nullptr){
                        tem->before->next = tem->next;
                        if(tem->next!=nullptr){
                            tem->next->before=tem->before;
                        }
                        else{
                            this->tail = tem->before;
                        }
                        tem->next=this->head;
                        this->head->before = tem;
                        tem->before = nullptr;
                        this->head = tem;
                    }
                    tem->val = value;
                    return ;
                }
                tem = tem->next;
            }
            //确认有元素,没查到插入
            if(this->listSize>=this->capacity){//插入新值,移除尾部
                listNode* newHead = new listNode(key,value,this->head,nullptr);
                this->head->before = newHead;
                this->head = newHead;
                this->tail->before->next = nullptr;
                this->tail = this->tail->before;
            }
            else{//插入新值
                listNode* newHead = new listNode(key,value,this->head,nullptr);
                this->head->before = newHead;
                this->head = newHead;
                this->listSize ++;
            }
            return;
        }

    }
};

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache* obj = new LRUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
```
- 看了下评论区,没有提供解法,说是用双向链表和map就好,map存key与对应节点指针,方便快速查找.
- 我再做一遍,用map存储<key,listNode*>对,成功了,时间击败74%,空间93%
- 代码如下
```cpp
class listNode{
public:
    listNode* next = nullptr;
    listNode* before = nullptr;
    int val;
    int key;
    listNode(int key,int val){
        this->key = key;
        this->val = val;
    };
    listNode(int key,int val,listNode* next,listNode* before){
        this->key = key;
        this->val = val;
        this->next = next;
        this->before = before;
    };
};

class LRUCache {
public:
    int capacity;
    listNode* head;
    listNode* tail;
    int listSize = 0;
    map<int,listNode*> m;
    LRUCache(int capacity) {
        this->capacity = capacity;
        this->head = nullptr;
        this->tail = nullptr;
    }
    
    int get(int key) {
        if(m.count(key)){
            listNode* tem =m[key];//修改
            // while(tem!=nullptr){//改为map查找
                if(tem->key==key){
                    if(tem->before!=nullptr){
                        tem->before->next = tem->next;
                        if(tem->next!=nullptr){
                            tem->next->before=tem->before;
                        }
                        else{//移动尾部需要修改tail
                            this->tail = tem->before;
                        }
                        tem->next=this->head;
                        this->head->before = tem;
                        tem->before = nullptr;
                        this->head = tem;
                    }
                    return tem->val;
                }
                // tem=tem->next;
            //}

        }

        return -1;
    }
    
    void put(int key, int value) {
        
        if(this->head == nullptr){//初始化
            this->head = new listNode(key,value);
            m[key] = this->head;//插入新项
            this->tail = this->head;
            this->listSize ++;
            return ;
        }
        else{//map查找,查到了搬回首位,注意尾部移动需修改tail
            if(m.count(key)){
                listNode* tem = m[key];//修改
                // while(tem!=nullptr){
                    if(tem->key == key){
                        if(tem->before!=nullptr){
                            tem->before->next = tem->next;
                            if(tem->next!=nullptr){
                                tem->next->before=tem->before;
                            }
                            else{
                                this->tail = tem->before;
                            }
                            tem->next=this->head;
                            this->head->before = tem;
                            tem->before = nullptr;
                            this->head = tem;
                        }
                        tem->val = value;
                        return ;
                     }
                    // tem = tem->next;
                //}
            }

            //确认有元素,没查到插入
            if(this->listSize>=this->capacity){//插入新值,移除尾部
                listNode* newHead = new listNode(key,value,this->head,nullptr);
                m[key] = newHead;//m插入新项
                this->head->before = newHead;
                this->head = newHead;
                //map移除
                m.erase(this->tail->key);
                this->tail->before->next = nullptr;
                this->tail = this->tail->before;
            }
            else{//插入新值
                listNode* newHead = new listNode(key,value,this->head,nullptr);
                m[key] = newHead;//m添加项
                this->head->before = newHead;
                this->head = newHead;
                this->listSize ++;
            }
            return;
        }

    }
};

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache* obj = new LRUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
```