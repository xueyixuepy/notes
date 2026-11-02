# 一.下载好相关的包
# 二.下载vscode的c有关的拓展
# 三.配置launch.json和task.json
## 1.作用
- **launch.json：告诉 VSCode【怎么启动调试器 gdb】**（调试配置）
- **tasks.json：告诉 VSCode【怎么编译代码】**（编译任务）
两个文件都放在 `.vscode` 文件夹里，是 VSCode 专门给 C/C++ 调试用的配置文件，**只在当前这个项目文件夹生效**，换别的 cpp 文件夹要重新生成 / 复制。
## 2. launch.json（调试启动配置）
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "name": "g++ build and debug", // 调试名字，调试面板会显示
            "type": "cppdbg",               // 调试类型：C++ GDB调试
            "request": "launch",            // 模式：启动程序调试
            "program": "${fileDirname}\\${fileBasenameNoExtension}.exe", 
            // 要运行的程序：当前cpp文件编译出来的exe
            "args": [],                     // 传给程序的命令行参数，暂时空着
            "stopAtEntry": false,           // true=程序一运行就停在main第一行；false=遇到断点才停
            "cwd": "${fileDirname}",        // 程序运行的工作目录，就是cpp所在文件夹
            "externalConsole": true,       // false：在VSCode内置终端跑；true：弹出独立黑窗口(最好是true,内置终端会有好多日志信息)
            "MIMode": "gdb",                // 指定调试器是gdb
            "miDebuggerPath": "D:\\MSY64\\ucrt64\\bin\\gdb.exe", 
            // gdb调试器的完整路径
            "setupCommands": [
                {
                    "description": "Enable pretty-printing for gdb",
                    "text": "-enable-pretty-printing",
                    "ignoreFailures": true
                }
            ],
            "preLaunchTask": "build" // 按下F5之前，先去执行名叫build的编译任务
            // "logging": {
            //     "moduleLoad": false,    // 关闭一大堆库加载日志
            //     "engineLogging": false,
            //     "trace": false
            // }这个是给MSVC（C++ Windows）调试器用的，`C++(GDB/LLDB)` 不认识它.但是我要用的是C++(GDB/LLDB),所以别用这个了.
        }
    ]
}
```
> `${xxx}` 是 VSCode 内置变量：
> `${file}` = 当前打开的 cpp 文件
> `${fileDirname}` = cpp 所在文件夹
> `${fileBasenameNoExtension}` = 文件名（不带.cpp 后缀）

## 3. tasks.json（编译任务）
```json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "build", // 任务名字！必须和上面preLaunchTask的值一模一样，对应上
            "type": "shell",  // 在终端shell里执行命令
            "command": "g++", // 用g++编译器
            "args": [
                "-g",                // 加上调试信息，不加gdb没法断点调试！
                "${file}",           // 要编译的源文件
                "-o",
                "${fileDirname}\\${fileBasenameNoExtension}.exe" // 输出exe名字
            ],
            "group": {
                "kind": "build",
                "isDefault": true
            },
            "problemMatcher": "$gcc" // 抓取g++的报错，在VSCode标红提示
        }
    ]
}
```
> 等价于手动敲命令：
> `g++ -g main.cpp -o main.exe`
## 4.按下 F5 之后完整流程
1. VSCode 读 `launch.json`，发现 `preLaunchTask: "build"`
2. 去 `tasks.json` 找到 label=build 的任务，执行编译：`g++ -g xxx.cpp -o xxx.exe`
3. 编译成功后，调用 `miDebuggerPath` 指定的 `gdb.exe`，启动调试、加载 exe
4. 碰到断点就停下，支持单步、看变量
>因为是在写好的CPP文件里使用F5,所以参数替换的都是当前CPP的名字,路径
## 5.位置
自己写的cpp文件和.vscode在同级
>可以同时存在多个有main的cpp文件
# 四.手动编译方法
g++ main.cpp -o main.exe
main.exe

