# 记录
- 题目：
  给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 小的元素（k 从 1 开始计数）。
- 思路：
  没什么好说的，中序遍历严格递增
- 代码：
  ```cpp
  /**
  * Definition for a binary tree node.
  * struct TreeNode {
  *     int val;
  *     TreeNode *left;
  *     TreeNode *right;
  *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
  *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
  *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
  * };
  */
  class Solution {
  public:
      vector<int> v;
      int k;
      int kthSmallest(TreeNode* root, int k) {
          this->k = k;
          tool(root);
          return this->v[k-1];
      }
      void tool(TreeNode* root){
          if(root == nullptr){
              return;
          }
          tool(root->left);
          if(this->v.size()<=k){
              this->v.emplace_back(root->val);
          }
          tool(root->right);

      }
  };
  ```
- 结果：
  时间100%，内存58%