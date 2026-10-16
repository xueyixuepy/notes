# 记录
---
- 题目：
  给定一个长度为 n 的 0 索引整数数组 nums。初始位置在下标 0。
  每个元素 nums[i] 表示从索引 i 向后跳转的最大长度。换句话说，如果你在索引 i 处，你可以跳转到任意 (i + j) 处：
  0 <= j <= nums[i] 且
  i + j < n
  返回到达 n - 1 的最小跳跃次数。测试用例保证可以到达 n - 1。
- 示例：
  示例 1:
  输入: nums = [2,3,1,1,4]
  输出: 2
  解释: 跳到最后一个位置的最小跳跃数是 2。
      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
  示例 2:
  输入: nums = [2,3,0,1,4]
  输出: 2
---
- 思路：
  其实参考了“跳跃游戏”的评论区，那里提到了正序遍历的一种方法。见笔记.
  所以这题我也用正序遍历.
  对于当前位置i,遍历其可能到达的位置,挑选其中能到达最远位置的,如果有多个则取最后一个(因为可能是终点).
  然后i跳到该位置,计数+1
  到了最远位置,结束
  返回计数值
- 代码:
  ```cpp
  class Solution {
  public:
      int jump(vector<int>& nums) {
          int maxHp = 0;
          int cout = 0;
          int cur = 0;
          int i = 0;
          while(true){
              if(i == nums.size()-1) break;
              for(int j = i+1;j<nums[i]+i+1&&j<nums.size();j++){
                  if(j == nums.size()-1){
                      cur = j;
                      break;
                  }
                  if(nums[j]+j-cur>=maxHp){
                      maxHp = nums[j];
                      cur = j;
                  }
              }
              maxHp = 0;
              i = cur;
              cout++;
          }
          return cout;
      }
  };
  ```
- 结果：
  时间100，空间99
---
- 题解的做法:
  - 法二(正向维护能到达的最远位置)
    代码:
    ```cpp
    class Solution {
    public:
        int jump(vector<int>& nums) {
            int maxPos = 0, n = nums.size(), end = 0, step = 0;
            for (int i = 0; i < n - 1; ++i) {
                if (maxPos >= i) {
                    maxPos = max(maxPos, i + nums[i]);
                    if (i == end) {
                        end = maxPos;
                        ++step;
                    }
                }
            }
            return step;
        }
    };
    ```
    这里记录的maxPos = i+nums[i];
    即当前i所能到达的最远位置.
    如果maxPos没更新,说明遍历过程中没遇到能到更远位置的点.
    更新了则说明遇到了
    且在到达该位置前,一定会更新,因为题目要求一定能到终点.
    更新次数不代表跳跃次数,而是超出当前maxPos才是.因为当前点跳跃距离内可能有多个点满足,而且更新之后一定可以超出边界,所以用超出边界来判断是否跳跃.