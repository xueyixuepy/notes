# 记录
- 题目
编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
每行的元素从左到右升序排列。
每列的元素从上到下升序排列。
  - 输入：matrix = 
    \[\[1,4,7,11,15],
    \[2,5,8,12,19],
    \[3,6,9,16,22],
    \[10,13,14,17,24],
    \[18,21,23,26,30]],
     target = 5
  - 输出：true
---
- 做这一题出了不少问题，首先是我的想法1
    首先我的思路是沿对角线去搜索，对角线搜索到了就返回true，没搜索到，发现比目标大的情况，则说明目标一定在当前位置的右上角与左下角两块中，所以就继续递归搜索，每次运行都需要判断是不是正方形，不是则分割，是则沿对角线搜索，没找到则返回两块结果的或值。对于每一块，只需要记录他的左上与右下即可
    这是我的代码：
    ```cpp
    class Solution {
    public:
        bool searchMatrix(vector<vector<int>>& matrix, int target) {
            vector<int> tem1 = {0,0};
            vector<int> tem2(2);
            tem2[0] = matrix.size()-1;
            tem2[1] = matrix[0].size()-1;
            return tool(matrix,tem1,tem2,target);
        }
        bool tool(vector<vector<int>>& matrix,vector<int> lt,vector<int> rb,int target){
            vector<int> tem1(2);
            vector<int> tem2(2);
            vector<int> tem3(2);
            vector<int> tem4(2);
            int tem;
            if(lt[0]>rb[0] || lt[1]>rb[1]) return false;
            if(rb[1]-lt[1]>rb[0]-lt[0]){
                
                tem1[1]=lt[1]+rb[0]-lt[0];
                tem1[0]=rb[0];
                tem2[0]=lt[0];
                tem2[1]= tem1[1]+1;
                return tool(matrix,lt,tem1,target)|tool(matrix,tem2,rb,target);
            }
            else if(rb[1]-lt[1]<rb[0]-lt[0]){
                tem1[1]=rb[1];
                tem1[0]=lt[0]+rb[1]-lt[1];
                tem2[0]=tem1[0]+1;
                tem2[1]=lt[1];
                return tool(matrix,lt,tem1,target)||tool(matrix,tem2,rb,target);
            }
            else{
                for(int i=lt[0], j=lt[1];i<=rb[0]&&j<=rb[1];i++,j++){
                    tem=matrix[i][j];
                    if(matrix[i][j]==target){
                        return true;
                    }
                    else{
                        if(matrix[i][j]>target){
                            tem1[0]=lt[0];
                            tem1[1]=j;
                            tem2[0]=i-1;
                            tem2[1]=rb[1];
                            tem3[0]=i;
                            tem3[1]=lt[1];
                            tem4[0]=rb[0];
                            tem4[1]=j-1;
                            return tool(matrix,tem1,tem2,target)||tool(matrix,tem3,tem4,target);
                        }
                    }
                }
            }
            return false;
        }
    };
    ```
    然而,在编写过程出了不少语法错误,要记住

    - ==或与与都是两个||或者&&==.
    - 然后是for语句中条件判断要用&&而不是加个逗号
    - ==最后就是vector向量未初始化不能直接用[]来赋值，得先提前用vector<int> tem1(2)这样初始分配==;

    遗憾的是，这个解法解出了81个情况，但是后续就超时了。看情况应该不是进入循环,而是这题对时间要求很严格.

- 正确的解法很妙,他是从右上角开始的,如果目标比它大,就往下移,比它小就往左移,每次都直接抛弃掉一整行或一整列.

```cpp
bool searchMatrix(vector<vector<int>>& matrix, int target) {
    if(matrix.empty() || matrix[0].empty()) return false;
    int i = 0, j = matrix[0].size()-1;
    while(i<matrix.size() && j>=0){
        if(matrix[i][j]==target) return true;
        else if(matrix[i][j]>target) j--;
        else i++;
    }
    return false;
}
```
    
    