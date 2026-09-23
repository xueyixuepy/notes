# git的使用
- **前置准备**
  安装 Git
  在网页上注册 GitHub/Gitee，新建一个空白远程仓库，复制仓库 HTTPS 地址
  首次使用 Git，配置全局用户名邮箱（仅执行一次）
  ```git
  git config --global user.name "你的名字"
  git config --global user.email "你的注册邮箱"
  # 查看配置
  git config --global --list
  ```
- **补充,重新登录**
  - 步骤1:在 Github 网页生成 PAT 令牌
    登录 github.com，右上角头像 → Settings
    页面拉到底，点 Developer settings
    选择 Personal access tokens → Tokens (classic)
    点 Generate new token (classic)
    Note：随便写，例如 vscode-git-notes
    Expiration（有效期）：可选 No expiration（永久，记得不用的时候删除）
    Scopes 权限：一定要勾选 repo（全部 repo 子选项），这是读写仓库必需权限
    拉到最下面，点 Generate token
    立刻复制生成的令牌！页面刷新后就再也看不到这个字符串，保存好！
  - 步骤2:删除 Windows 系统缓存的旧错误凭证
    Windows 会记住上一次错误登录信息，就算重新 push，它还会复用旧信息，持续报错。
    - Win 键搜索：凭据管理器，打开
      选择 Windows 凭据
      在列表找到名字包含 github.com 的条目，直接删除。
  - 步骤3:重新推送
    ```git push```
    弹出登录框：
    Username：github 用户名
    Password：粘贴刚才生成的令牌
  - 等价方案
    ```git
    # 格式: git remote set-url origin https://用户名:令牌@github.com/仓库地址
    git remote set-url origin https://xueyixuepy:PAT字符串@github.com/xueyixuepy/notes.git
    ```
- **补充,SSH模式连接仓库**
  不再需要每次输入 token，只需要配置 SSH 密钥：
  1. 本地生成 ssh 密钥
  ```ssh-keygen -t ed25519 -C "你的github注册邮箱"```
  一路回车，不用设置密码。
  1. 在用户目录.ssh文件夹打开 id_ed25519.pub，复制全部内容
  2. github 网页：头像→Settings→SSH and GPG keys → New SSH key，粘贴公钥保存
  3. 修改仓库远程地址为 SSH 版本
  ```git remote set-url origin git@github.com:xueyixuepy/notes.git```
  
  之后git push直接免密推送，不会再出现 token 认证问题。
- **配置仓库**
  - 场景A:
    本地文件夹还没有 Git 仓库（最常用）,进入你的项目文件夹，打开终端（powershell /cmd/bash）
    ```git
    # 1. 初始化本地git仓库（只执行1次，生成隐藏的 .git 文件夹）
    git init

    # 2. 把当前所有文件加入【暂存区】
    git add .
    # 只上传单个文件：git add swapPairs.cpp

    # 3. 提交到【本地版本库】，引号内写本次修改说明
    git commit -m "feat: 链表算法代码"

    # 4. 关联远程仓库，origin是远程仓库默认别名（只执行1次）
    git remote add origin https://gitee.com/xxx/your-repo.git

    # 5. 推送到远程 main 分支，-u 绑定默认分支，后续直接 git push
    git push -u origin main
    ```
  - 场景B:
    网页建仓库时勾选了 README.md（远程已有文件）,直接 push 会报错，需要先拉取远程文件合并：
    ```
    git pull origin main --allow-unrelated-histories
    git push -u origin main
    ```
- **后续日常更新代码**
  ```git
  git add .
  git commit -m "fix: 修复链表递归bug"
  git push
  ```
- **常用命令**
  ```
  git status          # 查看哪些文件改动，推荐经常看
  git remote -v       # 查看绑定的远程仓库地址
  git log             # 查看所有提交记录
  git remote set-url origin https://xxx.git      #修改远程地址
  ```
- **忽略文件**
  新建 .gitignore文件
  - 存放在仓库根目录
  - 文件名严格为：```.gitignore```
  - 基础语法
    ```
    # #开头是注释
    *.exe         # 匹配所有 .exe 文件（任意目录下）
    build/        # 匹配整个build文件夹（末尾 / 代表文件夹）
    test.txt      # 精确匹配这个文件名
    !main.cpp     # ! 取反：不忽略这个文件（优先级高，写在忽略规则后面）
    **/log*.log   # ** 递归匹配所有/log目录及所有子目录的.log文件
    ```
  - 检查是否忽略
    ```git check-ignore test.exe```
    如果输出test.exe,表示test.exe确实被忽略了
  - 
- **AI总结**
  git init：把文件夹变成本地仓库
  git add：工作区 -> 暂存区
  git commit：暂存区 -> 本地版本库
  git push：本地版本 -> 远程仓库
  git pull：远程仓库 -> 拉到本地  
  git clone 仓库地址 -> 是下载远程仓库到本地
