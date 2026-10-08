### 在此记录一些以前不知道但有用的知识
- **vscode把 Tab 改成一次缩进 2 空格**
  - 打开设置，搜索 tabSize,将 Editor: Tab Size 修改为 2
    搜索insert space,确保 Editor: Insert Spaces 勾选（按 Tab 插入空格，不是制表符）
  - 或者ctrl + shift + P,搜索preference: open user settings(JSON),在大括号里写    
    ```JSON
    "editor.insertSpaces": true,
    "editor.tabSize": 2,
    ```
  ```
  奇怪的是,一开始用设置的方法(方法一)没有用!!,然后我试着在json文件里写入ai生成的针对.md文件的配置,然后保存之后就生效了,我注意到在写入之前json文件里已经有了"editor.tabSize": 2,这样的语句,所以我给ai里生成的删掉了.但是我想测试原来的配置是不是只是影响不到.md文件所以把ai生成的配置都注释掉再保存后新建cpp文档,发现tab键确实是两个空格缩进,然而奇怪的是我再测试.md文件时又也是两个空格缩进了,相当于我只用了方法一,然后打开json文件按下了保存键就生效了.
  ```
- **vscode快速打开终端**
  ctrl+`(反引号)