# 记录：
- 题目：
  Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补全和拼写检查。
  请你实现 Trie 类：
  Trie() 初始化前缀树对象。
  void insert(String word) 向前缀树中插入字符串 word 。
  boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
  boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
- 示例：
  输入
  `["Trie", "insert", "search", "search", "startsWith", "insert", "search"]`
  `[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]`
  输出
  `[null, null, true, false, true, null, true]`
  解释
  Trie trie = new Trie();
  trie.insert("apple");
  trie.search("apple");   // 返回 True
  trie.search("app");     // 返回 False
  trie.startsWith("app"); // 返回 True
  trie.insert("app");
  trie.search("app");     // 返回 True
- 思路：
  想了好久，想着是不是像之前一样用边集，但是这题用边集的话很麻烦，因为字符可能重复。apple和alex的l是不一样的。
  所以还是构造树，每个结点都有一个next列表，记录它下一个可能的字符。
  还需要判断当前结点是否为曾经一个单词最后的字符（代码中实际上是在它后面插了一个空结点标记）。因为可能出现app和apple这种一个单词是前缀的情况。
  然后基本上就是递归了，插入部分遍历他的next列表，插入新值或者继续下去。其他都差不多形式
- 代码：
  ```cpp
  class Trie {
  public:
      vector<Trie*> next;
      char val = ' '; 
      bool isEnd = false;
      Trie(char val) {
          this->val = val;
      }
      Trie() {
      }
      
      void insert(string word) {
          if(word.size()==0) {
              isEnd = true;
              return;
          }
          for(int i = 0;i<next.size();i++){
              if(next[i]->val == word[0]){
                  next[i]->insert(word.substr(1));
                  return;
              }
          }
          Trie* newTree = new Trie(word[0]);
          newTree->insert(word.substr(1));
          next.emplace_back(newTree);
      }
      
      bool search(string word) {
          if(word.size()==0)
          {
              if(isEnd == true) return true;
              return false;
          }
          for(int i =0;i<next.size();i++){
              if(next[i]->val==word[0]){
                  return next[i]->search(word.substr(1));
              }
          }
          return false;
      }
      
      bool startsWith(string prefix) {
          if(prefix.size()==0) return true;
          for(int i =0;i<next.size();i++){
              if(next[i]->val==prefix[0]){
                  return next[i]->startsWith(prefix.substr(1));
              }
          }
          return false;
      }
  };

  /**
  * Your Trie object will be instantiated and called as such:
  * Trie* obj = new Trie();
  * obj->insert(word);
  * bool param_2 = obj->search(word);
  * bool param_3 = obj->startsWith(prefix);
  */
  ```
- 结果：
  时间5，空间5.
- 题解的做法
  字典树：
  利用字符只为小写字母的特性，构造26叉树。
  利用索引来节省时间。
  代码：
  ```cpp
  class Trie {
  private:
      vector<Trie*> children;
      bool isEnd;

      Trie* searchPrefix(string prefix) {
          Trie* node = this;
          for (char ch : prefix) {
              ch -= 'a';
              if (node->children[ch] == nullptr) {
                  return nullptr;
              }
              node = node->children[ch];
          }
          return node;
      }

  public:
      Trie() : children(26), isEnd(false) {}

      void insert(string word) {
          Trie* node = this;
          for (char ch : word) {
              ch -= 'a';
              if (node->children[ch] == nullptr) {
                  node->children[ch] = new Trie();
              }
              node = node->children[ch];
          }
          node->isEnd = true;
      }

      bool search(string word) {
          Trie* node = this->searchPrefix(word);
          return node != nullptr && node->isEnd;
      }

      bool startsWith(string prefix) {
          return this->searchPrefix(prefix) != nullptr;
      }
  };
  ```
  解析：
  - 插入是直接看看当前树对应字符对应子树是否存在，不存在则新建，存在则进入该子树
  - 查找和查前缀。
    这里新建了一个函数，效果是查找该字符串作为前缀查找到的字符串最后一个字符的结点。
    如果返回非空，说明有该前缀。
    如果结点是终止结点，说明有该单词。
  