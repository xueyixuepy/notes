# 记录
- 题目:
  给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
  进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
- 示例:
  示例 1：
  输入：nums = [1,1,1,2,2,3], k = 2
  输出：[1,2]
  示例 2：
  输入：nums = [1], k = 1
  输出：[1]
  示例 3：
  输入：nums = [1,2,1,2,1,2,3,1,3,2], k = 2
  输出：[1,2]
- 思路:
  我想着做进阶要求嘛,要求nlogn,
  上一题里我维护一个k大小的小顶堆,时间复杂度是nlogk.
  这题我试着用同样的方法,
  一开始想的是在遍历过程中统计出现次数,然后发现次数大于堆首的次数,则交换.**这个想法是错的**,因为在没有交换的时候,堆内元素的次数也在悄悄改变,堆的结构被破坏.
  所以干脆先统计完所有出现的元素及对应次数,然后再对这些元素进行维护k大小的堆,时间是n+nlogk = nlogn
- 代码:
  ```cpp
  class Solution {
  public:
      map<int,int> m;
      vector<int> topKFrequent(vector<int>& nums, int k) {
          vector<int> res(k,-10001);
          vector<int> numsAll;
          m[-10001] = 0;
          for(int i = 0;i<nums.size();i++){
              if(!m.count(nums[i])){
                  m[nums[i]] = 1;
                  numsAll.emplace_back(nums[i]);
              }
              else{
                  m[nums[i]]++;
              }
          }
          for(int i = 0;i<numsAll.size();i++){
              if(m[numsAll[i]]>m[res[0]]){
                  res[0] = numsAll[i];
                  minHeapDown(res,res.size(),0);
              }
          }

          return res;
      }
      void minHeapDown(vector<int>& nums,int heapSize,int i){
          while(true){
              int left = 2*i + 1;
              int right = 2*i +2;
              int minIdx = i;
              if(left<heapSize&&m[nums[left]]<m[nums[minIdx]]) minIdx = left;//注意比较的是minIdx对应的值，而不是i对应的。
              if(right<heapSize&&m[nums[right]]<m[nums[minIdx]]) minIdx = right;
              if(minIdx == i) break;
              swap(nums[i],nums[minIdx]);
              i = minIdx;
          }
      }
  };
  ```
  ==注意:map类型的count用法是m.count(key)而不是m[key].count()==
  ==注意:下降函数中用于比较的是left,right,minIdx,用不到i.==
- 结果:
  我觉得理论上时间复杂度是那样呀,豆包也说是nlog(n)
  但是时间5,空间80.
- 题解的做法
  - 法一:
    和我的一样欸,但是堆用的是一个priority_queue类型
    然后官方用 unordered_map（哈希）而不是 map 红黑树
    我把map改掉之后时间击败70%了.
    - 代码:
    ```cpp
    class Solution {
    public:
        static bool cmp(pair<int, int>& m, pair<int, int>& n) {
            return m.second > n.second;
        }

        vector<int> topKFrequent(vector<int>& nums, int k) {
            unordered_map<int, int> occurrences;
            for (auto& v : nums) {
                occurrences[v]++;
            }

            // pair 的第一个元素代表数组的值，第二个元素代表了该值出现的次数
            priority_queue<pair<int, int>, vector<pair<int, int>>, decltype(&cmp)> q(cmp);
            for (auto& [num, count] : occurrences) {
                if (q.size() == k) {
                    if (q.top().second < count) {
                        q.pop();
                        q.emplace(num, count);
                    }
                } else {
                    q.emplace(num, count);
                }
            }
            vector<int> ret;
            while (!q.empty()) {
                ret.emplace_back(q.top().first);
                q.pop();
            }
            return ret;
        }
    };
    ```
    - 解析:
      对于priority_queue：
      `priority_queue<pair<int, int>, vector<pair<int, int>>, decltype(&cmp)> q(cmp);
      `
      `priority_queue` 模板原型：`template< class T, class Container = vector<T>, class Compare = less<typename Container::value_type> >
      class priority_queue;
      `
      三个模板参数：
      1. `T`：堆里存的元素类型
      2. `Container`：底层存储容器，默认`vector`
      3. `Compare`：**比较器类型**
      
      `decltype(&cmp)` → Compare（返回比较器的类型）
      priority_queue 是**大顶堆逻辑**：
      `comp(a,b)` 返回 `true` → **a 的优先级 < b，b 排在上面**

  - 法二:
    基于快速排序.