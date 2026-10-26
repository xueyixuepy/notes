# 记录
---
- 题目：
  给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
  子数组是数组中元素的连续非空序列。
- 示例：
  示例 1：
  输入：nums = [1,1,1], k = 2
  输出：2
  示例 2：
  输入：nums = [1,2,3], k = 3
  输出：2
---
- 思路：
  不可用用回溯，因为得是连续的
  于是想到用动态规划，见注释
- 代码：
  ```cpp
  class Solution {
  public:
      int subarraySum(vector<int>& nums, int k) {
          //f(i) = [:i+1]的和
          //g(i,j) = [i:j+1]的和
          //f(i) = f(i-1)+nums[i]
          //g(i,j) = f(j) - f(i-1)
          int n = nums.size();
          int cout = 0;
          vector<int> f(n+1,0);
          //vector<vector<int>> g(n+1,vector<int>(n+1,0));
          for(int i = 1;i<=n;i++){
              f[i] = f[i-1]+nums[i-1];
          }
          for(int i = 1;i<=n;i++){
              for(int j = i;j<=n;j++){
                  if(f[j]-f[i-1] == k) cout++;

              }
          }
          return cout;
      }

  };
  ```
- 结果:
  时间5,空间88
---
**自己优化一下写法**
- 思路
  可以在第一次遍历的时候顺带进行子串的查找
- 代码:
  ```cpp
  class Solution {
  public:
      int subarraySum(vector<int>& nums, int k) {
          //f(i) = [:i+1]的和
          //g(i,j) = [i:j+1]的和
          //f(i) = f(i-1)+nums[i]
          //g(i,j) = f(j) - f(i-1)
          int n = nums.size();
          int cout = 0;
          vector<int> f(n+1,0);
          //vector<vector<int>> g(n+1,vector<int>(n+1,0));
          for(int i = 1;i<=n;i++){
              f[i] = f[i-1]+nums[i-1];
              for(int j = 1;j<=i;j++){
                  if(f[i]-f[j-1] == k) cout++;
              }
          }
          return cout;
      }
  };
  ```
- 结果
  时间5,空间88,没什么变化
---
**题解的思路(前缀和+哈希表)**
- 思路:
  g(i,j) = f(j) - f(i-1)
  当g(i,j) == k时,计数器++
  也就是当f(i-1) == f(j) - k时,计算器++
  只需要统计当前到了以j为结尾之前,有多少个f(i) == f(j) - k;
  所以用哈希表记录f(i):数量
  当遍历到了当前j时,前面的f(i)就都已经记录了,cout直接加上m[f(j) - k]就好
- 代码:
  ```cpp
  class Solution {
  public:
      int subarraySum(vector<int>& nums, int k) {
          unordered_map<int, int> mp;
          mp[0] = 1;
          int count = 0, pre = 0;
          for (auto& x:nums) {
              pre += x;
              if (mp.find(pre - k) != mp.end()) {
                  count += mp[pre - k];
              }
              mp[pre]++;
          }
          return count;
      }
  };
  ```