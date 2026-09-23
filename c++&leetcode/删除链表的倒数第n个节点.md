# 记录
- 题目
  给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
- 示例
  输入：head = [1,2,3,4,5], n = 2
  输出：[1,2,3,5]
- 思路
  使用快慢指针,快指针与慢指针相差距离为n,则当快指针到结尾时,慢指针指向目标节点的前指针
- 问题:
  - 需要新加一个头指针,处理删除节点为头指针时的情况
  - 最终返回应该是新头指针的下一个指针,用于处理头指针被删除的情况.
```cpp
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* removeNthFromEnd(ListNode* head, int n) {
        if(head == nullptr) return nullptr;
        ListNode* newHead = new ListNode(0,head);
        ListNode* k = newHead;
        ListNode* m = newHead;
        int cout=0;
        if(k->next==nullptr) return nullptr;
        while(k->next!=nullptr){
            if(cout>=n){
                m=m->next;
            }
            k = k->next;
            cout++;
        }
        m->next = m->next->next;

        return newHead->next;
    }
};
```
- 结果,时间击败100%,空间击败80%
- 他人的做法,用了递归,递归还是太妙了
```cpp
class Solution {
public:
    int cur=0;
    ListNode* removeNthFromEnd(ListNode* head, int n) {
       if(!head) return NULL;
       head->next = removeNthFromEnd(head->next,n);
       cur++;
       if(n==cur) return head->next;
       return head;
    }
};
```
- 解析:他这里用了一个全局变量cur,cur++放在```head->next = removeNthFromEnd(head->next,n);```之后,故而cur是在函数返回时++,函数首先会遍历到末尾,然后返回,cur++,cur等于n的时候,当前节点就是需要被删除的节点,故而返回下一个节点的指针.