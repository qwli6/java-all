Linux 常用命令集合

在 linux 命令行下使用 **top** 命令查看每个进程的情况



## 查看文件

**1.cat**	查看文件

cat -n  文件

查看文件，-n 表示带行号查看（带空白行）

cat -b 文件

查看文件，-b 表示不带空行

**2.more **	查看文件（可翻页）

按 Enter 键即可翻页，q 键即可退出查看文件

如果需要查找对应的元素，可以直接使用如下指令

```shell
> more login.jhtml
> /div
```

 使用 more 指令后，再次输入 /div，则可以从文件上面开始查找第一个出现 div 的行。如果需要查找下一个，直接按下指令 n 即可进行翻页。

**3.less**	查看文件

less 比 more 的功能更加丰富，more 只能向下翻页，向下查找。但是 less 不同，less 可以除了可以向下查找，还可以向上查找。

```shell
> less login.html
> /div  
```

输入 /dev 后，可以从上至下开始查找，输入 ?dev 后，可以从上查找。此时输入 n，则可以查找上（下）一个元素（区别在是输入了 / 还是 ？）

根据 pageDown 或者 pageUp 可以上下翻页。



## 数据截取

**1.head**  	显示前面几行

```shell
> head -n 10 login.html
```

**2.tail** 	显示后面几行

```shell
> tail -n 10 login.html
> tail -f login.html
```

-f 表示持续刷新文件后续行数。按下 ctrl + c 才会终止刷新





## 磁盘和目录的容量

**1.df**	列出文件系统的整体磁盘使用量

df -h 是用的最多的一个命令，它以人们交易阅读的 GBytes，MBytes、KBytes 等格式自行显示

```shell
> df -h /Users/liqiwen
```

查看某一目录下的系统占用情况

```
 ~/Downloads > df -h /Users/liqiwen
Filesystem     Size   Used  Avail Capacity iused      ifree %iused  Mounted on
/dev/disk1s1  234Gi  155Gi   63Gi    72% 1968763 2447156597    0%   /System/Volumes/Data
```

**2.du** 	查看当前文件夹磁盘占用情况

该命令会列出所有的文件

## 文件压缩

压缩文件的扩展名通常为 `.tar`、`.tar.gz`、`.tgz`、`.gz`、`.Z`、`.bz2`、`.xz`

| 扩展名   | 解释                                      |
| -------- | ----------------------------------------- |
| .z       | compress 程序压缩的文件                   |
| .zip     | zip 程序压缩的文件                        |
| .gz      | gzip 程序压缩的文件                       |
| .bz2     | bzip2 程序压缩的文件                      |
| .xz      | xz 程序压缩的文件                         |
| .tar     | tar 程序打包的文件，并没有压缩过          |
| .tar.gz  | tar 程序打包的文件，并且经过 gzip 压缩    |
| .tar.bz2 | tar 程序打包的文件，并且经过 bzip2 的压缩 |
| .tar.xz  | tar 程序打包的文件，并且经过 xz 的压缩    |

