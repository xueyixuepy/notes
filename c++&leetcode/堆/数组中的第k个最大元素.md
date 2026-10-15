# 记录
- 题目：
  给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
  请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
  你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
  提示：
  1 <= k <= nums.length <= 105
  -104 <= nums[i] <= 104
- 思路：
  只知道要用堆解决。
- #### 堆！！
  堆是**完全二叉树**实现的一种**优先队列**，核心特点：父节点的值和子节点的值满足固定大小关系。
  ##### 两种堆
  1. **大顶堆（最大堆）**
  父节点 ≥ 子节点；堆顶（根）是整个集合**最大值**。
  1. **小顶堆（最小堆）**
  父节点 ≤ 子节点；堆顶（根）是整个集合**最小值**。
  > 只要求父子之间满足大小关系，**左右子节点之间没有大小要求**。
  ##### 存储方式
  堆一般用**数组**存储（利用完全二叉树下标规律）
  设数组下标从 `0` 开始：对于当前索引i
  - 父节点：`parent(i) = (i - 1) // 2`
  - 左孩子：`left(i) = 2*i + 1`
  - 右孩子：`right(i) = 2*i + 2`
- 思路2
  建堆吧,既然是找第k大的值,那建个大顶堆?[x]
  错了,建了大顶堆完全没用,根本不能确定第k大在左右哪边.
  问问ai,说要维护一个大小为k的小顶堆,其中只存储最大的k个数,这个堆的顶端就是第k大的
- 思路3
  好,该怎么维护这样一个堆呢,ai给我生成的函数里有大顶堆的下沉函数,改一改就是小顶堆的了,还有一个是建堆函数,但是把一个已知元素的vector变成堆的格式.
  于是自己处理,构造一个vector,大小为k,里面元素默认为$-10^4-1$,所有元素都比这个大.
  遍历原先vector,如果元素比堆顶大,则交换,然后下沉
- 代码:
  注:建堆函数,和一个大顶堆下沉函数没有用
  ```cpp
  class Solution {
  public:
      int findKthLargest(vector<int>& nums, int k) {
          //这题要找第k个最大的元素，所以应该做成大小为k小顶堆，里面只记录>=堆首的值，堆首就是第k大。
          vector<int> kHeap(k,-10000-1);
          for(int i = 0;i<nums.size();i++){
              if(nums[i]>kHeap[0]){
                  kHeap[0] = nums[i];
                  shiftDown2(kHeap,0);
              }
          }
          return kHeap[0];

      }
      void shiftDown1(vector<int>& arr, int i) {
          //把索引为i的位置的值和它子树中应该在的位置的值交换，子树也得是堆。
          int n = arr.size();
          while(true) {
              int left = 2*i + 1;
              int right = 2*i + 2;
              int maxIdx = i;
              if(left < n && arr[left] > arr[maxIdx]) maxIdx = left;
              if(right < n && arr[right] > arr[maxIdx]) maxIdx = right;
              if(maxIdx == i) break;//没发生交换，说明这部分左<-父->右是父亲最大,结束
              swap(arr[i], arr[maxIdx]);//发生了交换,则交换父与子的值,因为两个判断是独立的if,所以最后到父亲位置的一定是三者之间最大的那个.所以不用担心破坏结构,父亲始终是最大的.
              i = maxIdx;//i指向被交换值的子树的位置,然后判断这个子树与它的子树是否满足父最大.
          }
      }
      void shiftDown2(vector<int>& arr, int i) {
          //小顶堆
          int n = arr.size();
          while(true) {
              int left = 2*i + 1;
              int right = 2*i + 2;
              int minIdx = i;
              if(left < n && arr[left] < arr[minIdx]) minIdx = left;
              if(right < n && arr[right] < arr[minIdx]) minIdx = right;
              if(minIdx == i) break;
              swap(arr[i], arr[minIdx]);
              i = minIdx;
          }
      }
      void buildHeap(vector<int>& arr) {

          //当n等于2时,仍有效,小于2时,不操作,其实也是有效的.
          int n = arr.size();
          //从后往前遍历,可以发现每次进行都是把当前值往下移动到正确位置.
          for(int i = (n-2)/2; i >= 0; i--) {
              shiftDown2(arr,i);
          }
      }
  };
  ```
- 结果:
  时间13,空间39
  这个方法时间复杂度实际上是O(nlogk)
- 题解的做法:
  - 法一:
    构造快速排序
    时间复杂度为n
  - 法二:
    堆排序:
    代码:
    ```cpp
    class Solution {
    public:
        void maxHeapify(vector<int>& a, int i, int heapSize) {
            int l = i * 2 + 1, r = i * 2 + 2, largest = i;
            if (l < heapSize && a[l] > a[largest]) {
                largest = l;
            } 
            if (r < heapSize && a[r] > a[largest]) {
                largest = r;
            }
            if (largest != i) {
                swap(a[i], a[largest]);
                maxHeapify(a, largest, heapSize);
            }
        }

        void buildMaxHeap(vector<int>& a, int heapSize) {
            for (int i = heapSize / 2 - 1; i >= 0; --i) {
                maxHeapify(a, i, heapSize);
            } 
        }

        int findKthLargest(vector<int>& nums, int k) {
            int heapSize = nums.size();
            buildMaxHeap(nums, heapSize);
            for (int i = nums.size() - 1; i >= nums.size() - k + 1; --i) {
                swap(nums[0], nums[i]);
                --heapSize;
                maxHeapify(nums, 0, heapSize);
            }
            return nums[0];
        }
    };
    ```
    - 解析:
      这里maxHeapify等价与下沉函数,用的递归,但是限制大小为heapSize,是对vector的前heapSize个数进行操作.
      这是一个新思路,构造大顶堆.
      如果把vector的最大值取出k-1次,那么剩下的数构造的大顶堆,顶部就是第k大的值.
      这里是先原地构造大顶堆,时间为O(n);
      然后执行k-1次取出最大值,把最大值丢在后面,不参与堆排,然后对首位再进行下沉,每次是O(logn)的时间,
      总时间是O(n+klogn) = O(nlogn)
    - 结果
      时间复杂度比我的差欸.
    - 根据这个做法,我有了个想法,每次取出丢在后面,其实也是按升序排的,最大值排在最后面,所以进行n-1次取出,应该就是一个升序数组了,时间为O(n+nlogn)


  
  