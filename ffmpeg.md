## 下载&安装 ffmpeg

macos 安装有点问题，brew 一直会卡住，所以采用了下载的方式。下载  `ffmpeg-20200831-4a11a6f-macos64-static.zip`，是一个压缩文件，下载下来直接解压缩就好了。

[这个网站](https://ffmpeg.zeranoe.com/builds/)， 可惜这个网站在 2020-09-18 日就要被关闭了。

解压下载下来的 ffmpeg，放在系统的一个目录下，这里我放在了我自己新建的一个 develop 目录下：

**配置环境变量**

```shell
> vim ~/.bash_profile

#配置环境变量
export PATH=$PATH:/Users/liqiwen/develop/ffmpeg-20200831-4a11a6f-macos64-static/bin

#保存退出
> wq!
> source ~/.bash_profile
```

**验证 ffmpeg 是否安装成功**

```shell
 ~/Downloads > ffmpeg -version
ffmpeg version git-2020-08-31-4a11a6f Copyright (c) 2000-2020 the FFmpeg developers
built with Apple clang version 11.0.0 (clang-1100.0.33.8)
configuration: --enable-gpl --enable-version3 --enable-sdl2 --enable-fontconfig --enable-gnutls --enable-iconv --enable-libass --enable-libdav1d --enable-libbluray --enable-libfreetype --enable-libmp3lame --enable-libopencore-amrnb --enable-libopencore-amrwb --enable-libopenjpeg --enable-libopus --enable-libshine --enable-libsnappy --enable-libsoxr --enable-libsrt --enable-libtheora --enable-libtwolame --enable-libvpx --enable-libwavpack --enable-libwebp --enable-libx264 --enable-libx265 --enable-libxml2 --enable-libzimg --enable-lzma --enable-zlib --enable-gmp --enable-libvidstab --enable-libvmaf --enable-libvorbis --enable-libvo-amrwbenc --enable-libmysofa --enable-libspeex --enable-libxvid --enable-libaom --enable-libgsm --enable-librav1e --enable-libsvtav1 --enable-appkit --enable-avfoundation --enable-coreimage --enable-audiotoolbox
libavutil      56. 58.100 / 56. 58.100
libavcodec     58.101.101 / 58.101.101
libavformat    58. 51.101 / 58. 51.101
libavdevice    58. 11.101 / 58. 11.101
libavfilter     7. 87.100 /  7. 87.100
libswscale      5.  8.100 /  5.  8.100
libswresample   3.  8.100 /  3.  8.100
libpostproc    55.  8.100 / 55.  8.100
```

出现了 ffmpeg 的版本信息，则配置成功。

## ffmpeg 常用命令

- 将视频 MP4 转换成 gif

  ```shell
  > ffmpeg -i 原视频.mp4 转换后.gif
  ```

- 取视频的第几秒开始截取视频帧

  ```shell
  ## 从第十秒开始，截取三秒转换成动图
  > ffmpeg -t 3 -ss 00:00:10 -i 原视频.mp4 转换后.gif
  ```

- 转化高质量的 gif 图片（默认转化是中等质量的 gif）

  ```shell
  > ffmpeg -i 原视频.mp4 2048k 转换后.gif
  ```

- 缩放视频尺寸

  ```shell
  # 注意 sacle 值必须是偶数，这里的 -1 表示保持长宽比，根据宽度值自适应高度。
  # 如果要求压缩出来的视频尺寸长宽都保持为偶数，可以使用 -2
  > ffmpeg -i 原视频.mp4 -vf scale=360:-1 转换后.mp4
  ```

- 加倍速播放视频

  ```shell
  > ffmpeg -i input.mov -filter:v "setpts=0.5*PTS" output.mov
  
  定义帧率 16fps:
  
ffmpeg -i input.mov -r 16 -filter:v "setpts=0.125*PTS" -an output.mov
  慢倍速播放视频
ffmpeg -i input.mov -filter:v "setpts=2.0*
  ```

- 

- 

  
  
  
  
  ffmpeg -i input.mov -filter:v "setpts=0.5*PTS" output.mov
PTS" output.mov
  静音视频（移除视频中的音频）
ffmpeg -i input.mov -an mute-output.mov
  -an 就是禁止音频输出

  将 GIF 转化为 MP4
ffmpeg -f gif -i animation.gif animation.mp4
  也可以将 gif 转为其他视频格式
  
  ffmpeg -f gif -i animation.gif animation.mpeg

  ffmpeg -f gif -i animation.gif animation.webm
  获取 GIF 的第一帧图片
  使用 ImageMagick 可以方便第提取 gif 图片的第 N 帧图像。
  
  安装 ImageMagick
  
brew install imagemagick
  提取第一帧
  
  convert 'animation.gif[0]' animation-first-frame.gif
通过 [0] 就可以提取出 gif 的第一帧图像。