
功能
===
command包内是一个CLI程序的框架，可以简单的实现一个CLI界面。

使用方法
====

1. 定义自己的指令类继承Command类，一个类对应一个指令。
其需要实现以下函数:
>public void run()

指令的执行函数，Manager对输入的指令字符串进行解析后会调用对应类的run函数。

>public String commandLine()

这个函数用于定义本指令的指令字符串，比如类似copy del rm echo 等等。

>public String selfIntroduce(boolean inShort)

用于对本指令功能的介绍。inShort变量表示当前系统需要一个一行的短介绍还是详细介绍。
介绍字符串会被用于预定义的help指令，介绍会出现在help显示的指令列表中，
长结束会出现在help command 得到的具体指令介绍中。

2. 在指令内内部可以通过@ArgumentMark或者@OptionMark标记成员变量来获取参数，

@OptionMark标记一个选项，就是类似--force -u -R这种，
这个注解标记的成员变量的类型只能是：
  * boolean类型 表示一个开关型变量例如 `ls -a`的-a选项
  * String类型 表示一个有参数的选项 例如`git commit -m "message"`
    中的-m选项，它有一个参数内容"message"

OptionMark注解可以使用以下可选参数
  * value=这个Option在命令行中的名字,例如`--force`选项名字就是force
  * shortName=这个Option在命令行中的简称，只能是一个字母，
    例如`--force`可以简写做`-f`，简称名就是f

3. 在main函数中:
    1. new一个CommandManager对象
    2. commandManager.init(packageName) 来初始化管理器，
其会搜索包名下的所有Command类的子类，并注册。
    3. 然后可以调用
        * excuteCommand(String[]args)来执行一次指令
        * commandCycle()来进入Shell模式。

    >不同的CommandManager对象之间没有直接关系，可以对不同的指令集合分别建立Manager。

CLI界面选项格式
=========

框架使用GNU风格的参数格式，--option 表示长选项 -o 表示短选项
-- 表示后面一个以-开头的字符串不视为选项，视为普通字符串。对长选项
支持--option=value 或者 --option value两种格式，对短选项支持堆叠(例如`rm -Rd`)

使用Shell模式时，支持使用""  ''来输入包含空格的字符串，但行末没有配对的" '会被忽略
而不是像Bash一样续行，支持少量转意字符:
* \空格
* \ \
* \ "
* \ '
* \ t
* \ n