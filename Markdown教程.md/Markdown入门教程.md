<h1 style="text-align:center; font-family:Times New Roman; color:orange;">
  Markdown<span style="font-family:SimSun;">入门教程</span>
</h1>

<h2 style="text-align:center; font-family:Times New Roman; font-size:20pt;">
  作者
</h2>

- [一、准备工作](#一准备工作)
- [二、基本语法](#二基本语法)
- [三、 其他操作](#三-其他操作)
- [四、导出为PDF文档](#四导出为pdf文档)
- [五、查看写好的.md笔记](#五查看写好的md笔记)

---

## 一、准备工作

1. **安装 VS Code**  
   [vscode 官网下载地址](https://code.visualstudio.com/)

2. **下载必要的插件**
   - Markdown All in One  
   - Markdown Preview Enhanced  
   - Markdown PDF（不推荐）  
   - Paste Image  

3. **创建 `.md` 文档，右键打开侧边 预览功能，开始编辑**

---

## 二、基本语法

1. **标题**
    # 一级标题  
    ## 二级标题  
    ### 三级标题
2. **引用**
   >引用内容
3. **列表**
   1. 无序列表
   - 列表1
   + 列表2
   * 列表3 
   2. 有序列表
   3. 嵌套
   4. TodoList
      - [x] a
      - [ ] b
      - [ ] c
4. **表格**
    | 左对齐 | 居中对齐 | 右对齐 |
    |:-|:-:|-:|
    | a | b | c |
5. **段落**
   - 换行：两个以上空格后回车/空一行
   - 分割线：3个***
    ***

   - 字体（空一行，不然表格会被当做段落内容）
    
    | 字体 | 代码 |
    |:-:|:-:|
    | 斜体 | *斜体* |
    | 粗体 | **粗体** |
    | 斜粗体 | ***斜粗体*** |
    | 删除线 | ~~删除线~~ |
    | 下划线 | <u>下划线</u> |
    | 高亮 | ==高亮== |
   -  脚注
    请大家多多三连[^1]支持
6. **代码**
    ```cpp
    #include<iostream>  
    using namespace std;
    int main(){
        cout<<"hello world";
    }       
    ```
    ```python
    import math
    print("hello world")
7. **超链接**
   - 更多使用教程可以参考网站:https://www.runoob.com/markdown/md-link.html
   - <https://www.runoob.com/markdown/md-link.html>
   - https://www.runoob.com/markdown/md-link.html
   - 查看更多使用功能请[点击链接][教程]
   - 或者[点击链接](https://www.runoob.com/markdown/md-link.html)
8. **图片**
   - 使用图床保存图片（推荐：[路过图床](https://imgse.com/)）
   - 使用 Markdown 语法插入图片（点击可放大）：
      - 语法:
         \!\[图片替代文字](图片地址 "悬浮标题")
         
      ![小猫](小猫.png "小猫")

      [![pFZHwAe.jpg](https://s11.ax1x.com/2024/01/23/pFZHwAe.jpg)](https://imgse.com/i/pFZHwAe)
   - 使用 HTML 控制图片大小和位置（居中显示）： 
     <div align="center">
       <a href="https://imgse.com/i/pFZHwAe">
         <img src="https://s11.ax1x.com/2024/01/23/pFZHwAe.jpg" alt="pFZHwAe.jpg" width="60%" />
       </a>
     </div>

## 三、 其他操作
  **插入latex公式**
  - 行内显示公式:$f(x)=ax+b$
  - 块内显示数学表达式:
  $$
  \begin{Bmatrix}
  a & b \\
  c & d
  \end{Bmatrix}
  $$

**html/css语法**
   - ctrl+shift+p搜索
   "Markdown Preview Enhanced: customize css"在style中使用css语法改标题格式等
   - **个性化设置**
   File-Preferences-Settings
## 四、导出为PDF文档
   - 使用Markdown PDF(不推荐)
   - open in Browser->打印->手动存为PDF文档(存成PDF好像下划线和代码会有些变化)
## 五、查看写好的.md笔记
   - 右上角文本编辑器换成MARKDOWN增强预览

[教程]: https://www.runoob.com/markdown/md-link.html
[^1]: 脚注：点赞、投币、收藏!!


