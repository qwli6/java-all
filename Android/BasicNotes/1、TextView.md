# 引言
Android中的常用控件有很多。比如按钮Button，可以显示一段文本的TextView， 输入框EditText，显示图片ImageView，图片按钮ImageButton进度条ProgressBar，单选按钮RadioButton，多选框CheckBox，滚动条ScrollView等等，它们都是Android中的常用控件。

##TextView 以及ImageView和 View区别和联系
Android中的控件很多，其中绝大多数都是直接继承自View和ViewGroup的，同时ViewGroup又继承自View，所以，Android中一些常用的控件都是直接或者间接的继承自View的。所以这里也可以说View是TextView和ImageView的父类。Android里面的所有图形界面都是由View和ViewGroup组成的。
#####View
所有可视化控件的父类，提供组件描绘和时间处理方法。
#####ViewGroup
View类的子类，可以拥有子控件,可以看作是容器。Android UI中的控件其实和数据结构中的树有着相似的概念。都是按照层次结构堆叠而成。
而创建UI布局的方式有两种：使用Java代码实现或者通过XML定义布局！后者显得更加方便和容易理解，也是我们最常用的手段。
**我们一般很少直接用View和ViewGroup来写布局，更多的时候使用它们的子类控件或容器来构建布局！**
接下来说一下TextView和ImageView的具体用法。

### 1. 传统TextView
TextView 可以说是Andriod中最简单的控件了，我们知道它的作用是在界面上显示一条文本信息。下面我们来看看TextView的简单用法。
	
	    <TextView
	        android:gravity="center"    文字居中显示
	     	 android:background="@color/color2"    TextView控件的背景颜色
	        android:textColor="@android:color/black"  TextView控件的文字颜色
	        android:textSize="18sp"       文字大小，单位是sp
	        android:layout_width="200dp"    控件宽度
	        android:layout_height="200dp"   控件高度
	        android:textStyle="bold|italic"    文字样式： 粗体| 斜体
	        android:id="@+id/tv_text_view"    id
	        android:onClick="click"          点击事件响应方法
	        android:text="TextView文字控件">    文字内容
		</TextView>

上面是一个很普通的TextView控件，我们依次解释一下上面这个TextView的属性。注意：控件的属性是以key-value的形式存在的，前面代表属性名，后面代表属性值。

1. android:layout_width="200dp" 表示TextView控件的宽度，我们这里赋值为“200dp”
2. android:layout_height="200dp" 表示TextView控件的高度，我们这里同样也赋值为“200dp”
	注意：Android中的每一个控件都必须有宽度和高度的属性。
	宽高属性分别有三种可选值：match_parent（fill_parent）， wrap_content, "**dp"
	其中match_parent代表的是和父容器一样宽，如果你给一个TextView的宽高设置了match_parent，那么这个TextView将会和父容器重叠。fill_parent和match_parent是一个意思，只不过fill_parent在之前就已经被废弃了。google推荐我们使用match_parent
	wrap_content代表的是包裹自身的内容。TextView自身文字占多大空间，wrap_content就会表现出刚好包裹住TextView文字的大小
	“xxdp” 这个是代表自定义的大小，比如我上面所写的就是自定义的宽高。注意，不要写的太大，这样没什么意义。
3. android:textColor=“#f00”  文字显示颜色，后面的属性值可以是十六进制写法，也可以引用资源文件夹下的color.xml中的属性，我这里是十六进制的简写
4. android:textSize="18sp"   文字大小，单位通常是sp。
5. android:textStyle="bold|italic"  文字样式。同样有三种可选值：bold代表粗体， italic代表斜体，还有一种是normal，也就是正常显示。通常我们不写这个属性，默认都是normal的，如果你想TextView中的文字既是粗体，又是斜体。可以用“属性名|属性名”的方式实现。
6. android:id="@+id/tv_textview"    控件Id。相当于给这个控件取了一个名称，方便我们在Activity（活动）里面通过findViewById（）方法拿到这个控件。
7. android:text="TextView文字控件"    这个就是TextView显示在界面上的文字。
8. android:gravity="center"          文字居中显示。
9. android:onClick="click"          点击事件响应方法（这个你可能不是很明白，没关系，等到我们讲到控件的点击事件后，你就明白了）

基本上TextView的常用属性就这些，当然，TextView肯定不仅仅只是这一点属性。它的属性还有很多，比如“android:textAllCaps="true"”这个属性，我们知道是控制显示文字大小写的问题，但是这个属性在日常的开发中很少用到，所以就直接忽略了，其他属性也是一样。如果你们有兴趣，可以自行去官网了解一下TextView的其他属性。
	
运行截图演示 

![](http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-12-10%2023.30.37.png)

上面就是TextView的运行演示，基本上常用的属性就这些。

#### 2. 带有阴影的TextView

上面我们说了传统TextView的属性。下面我们来讲一下带有阴影的TextView以及圆角的TextView是如何实现的。
我们就不新建新的项目了，直接在现有的TextView上加上如下几个属性，顺便改一下TextView的显示文字属性将其更改成“android:text="我是带有阴影的TextView"”

			android:shadowColor="#0ff"   // 阴影的颜色
        	android:shadowDx="5.0"    // 横坐标方向阴影偏离多远
        	android:shadowDy="5.0"    // 纵坐标方向阴影偏离多远
        	android:shadowRadius="10.0"  //阴影的半径

好啦，我们帅气又带有阴影的TextView闪亮登场！运行截图如下：

![](http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-12-10%2023.42.02.png)

#### 3. 圆角的TextView

好了，下一个我们要讲的就是带有圆角的TextView，可能细心的同学已经注意到了，原生的TextView是方方正正的，而系统并没有给我们提供圆角的背景属性，那此时又需要圆角的属性，应该怎么办呢？ 莫慌，老司机来带带你。我们可以从背景方面下手。
1. 在drawable目录下新建一个shape.xml
2. 根元素为shape打头
3. 其中有如下六个节点，这些节点不是必须写的，自己可以根据项目中的实际情况进行选择

	<?xml version="1.0" encoding="utf-8"?>
	<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle">
	    <corners android:radius="5dp"/>
	    <solid android:color="#f00"/>
	    <stroke android:color="#0f0" android:width="5dp"/>
	    <gradient android:useLevel="false" android:startColor="@color/color1" android:centerColor="@color/color2" android:endColor="@color/color3"/>
	    <size android:height="200dp" android:width="200dp"/>
	    <padding android:bottom="10dp" android:left="20dp" android:right="30dp" android:top="40dp"/>
	</shape>	
	

六个节点分别是
>       <corners android:radius="5dp"/>  //设置角度
>       <solid android:color="#f00"/>  // 设置背景颜色
>       <stroke android:color="#0f0" android:width="5dp"/>  //设置边框颜色以及边框宽度
>       <gradient android:useLevel="false" android:startColor="@color/color1" android:centerColor="@color/color2" android:endColor="@color/color3"/>   // 设置渐变色： 开始颜色 中间颜色 结束颜色
>       <size android:height="200dp" android:width="200dp"/>  // 设置大小
>       <padding android:bottom="10dp" android:left="20dp" android:right="30dp" android:top="40dp"/>  //  设置间距

依然在上面的项目中，我们去掉阴影的属性，并且给TextView的背景属性设置为如下：
	
	android:background="@drawable/shape"
	
shape.xml 内容如下

	<?xml version="1.0" encoding="utf-8"?>
	<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle">
	    <corners android:radius="5dp"/>
	    <solid android:color="#f00"/>
	    <stroke android:color="@android:color/black" android:width="5dp"/>
	</shape>
	
上面的属性文件我属性我已经全部列举出来了，shape.xml文件中的内容我想同学们不难理解吧。下面贴一下演示图

![](http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-12-10%2023.58.56.png)


## 带有图片的TextView

有些时候，我们需要在TextView的旁边加上一张图片，有时候是左边，有时候是上边，这种方式用两个控件可以实现，但是如果我们使用了TextView，仅仅只需要一个属性就可以实现这样的效果。大大简化了系统绘制布局带来的压力。

	  android:drawableLeft="@mipmap/ic_launcher"
     android:drawableRight="@mipmap/ic_launcher"
     android:drawableTop="@mipmap/ic_launcher"
     android:drawableBottom="@mipmap/ic_launcher"

这些属性的意思想必我不解释各位也能够看明白。我就不多说了，在下面我贴一下效果图

![](http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-12-11%2000.12.16.png)

如果觉得图片和文字太挤，可以使用drawablePadding调整间距

	android:drawablePadding="50dp"
	
啦啦啦，见证奇迹的时刻到了！调整间距后的效果图，这里我为了效果明显，我刻意把间距调整的很开，在实际的应用中，3dp-5dp足够。
![](http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-12-11%2000.13.24.png)


## 其他属性

##### 1.单行显示

	 android:singleLine="true"   多余的数字用省略号代替
	 
	 android:maxLines="1"  这个属性也是单行显示，但是多出来的数字不会用省略号代替，该属性可以显示多行
##### 2.水平跑马灯效果

	 android:ellipsize="marquee" //跑马灯
     android:singleLine="true" // 单行，如果是多行，无法显示跑马灯效果
     android:marqueeRepeatLimit="marquee_forever" //重复次数： 无限重复
     
  android:ellipsize还有一些其他的属性：
  
> android:ellipsize="start"      // 省略号从头开始
> 
> android:ellipsize="middle"   // 省略号从中间开始
> 
> android:ellipsize="end"   // 省略号从末尾开始
> 
> android:ellipsize="marquee"   // 水平滚动跑马灯效果
> 
> android:ellipsize="none"    // 没有任何效果


## TextView响应点击事件
	
	android中的大部分控件都是可以响应点击事件的，这并不是按钮的专利。如下，我们就给一个TextView设置了一个点击事件。在点击事件的回调方法中，用Toast弹出了一句话。
	
	public class TextViewActivity extends AppCompatActivity {
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_text_view);
	        findViewById(R.id.tv_text_view).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                Toast.makeText(TextViewActivity.this, "我是一个textView", Toast.LENGTH_SHORT).show();
	            }
	        });
	    }
	}


![](http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-12-11%2001.20.40.png)


# 总结
好啦，本节就到这里了，讲解了TextView的大部分属性，当然也有覆盖不到的地方，如果小伙伴们有兴趣，可以自己上google去查一下对应的api。另外google中文站已经在中国发布了，这也变相的相当于google回到中国迈出了一小步，相信最后google一定会回归的。google的中文站点：[https://developers.google.cn/
](google官方中文站)，不需要翻墙，基本上和翻墙的官网一样。这也是android开发者的福音。







1、开写前的一堆废话

Android中的常用控件有很多。比如按钮Button，可以显示一段文本的TextView， 输入框EditText，显示图片ImageView，图片按钮ImageButton进度条ProgressBar，单选按钮RadioButton，多选框CheckBox，滚动条ScrollView等等，它们都是Android中的常用控件。
2、来不及解释了，快上车
快点上车，不用刷卡。先带你们加个油，先去了解一下几个专有名词，避免开车开到半路没油了，还是得回过头来加油。

No1. XML（Extensible Markup Language）
这货翻译成中文就是“可扩展的标记语言”。至于为什么要讲这个呢？不知道你们发现没有，你们手机上显示的一个个可以看见的界面，实际上都是用XML文件编写出来的。既然我们要去学Android开发，那我们首当其冲就应该搞定这个拦路虎。XML的内容其实有点多，不过我们现在仅仅只是Android开发入门，So，我们只需要了解一下XML的基本语法格式就可以了。放心，不难，而且忒容易。
[tex=code]语法 1  所有 XML 元素都须有关闭标签[/tex]
XML标签是对称的，有开始标签就必定会带有结束标签。嗯，下面举两个例子。

[tex=code]正确做法：
<data>hello</data>
<data></data>[/tex]

可以看到，上面的两个例子都是有打开区间和闭合区间的。只不过一个<data>标签中有值，一个<data>中没有值而已。

下面我再写一个栗子
[tex=code]错误做法：
<data><data/>
<data><data>
<data>
[/tex]
聪明的你们知道在哪里的么？如果你知道，就在心里默默的答出来。


[tex=code] 语法2 XML 标签对大小写敏感[/tex]


[tex=code] 语法3 XML 的属性值须加引号[/tex]

[tex=code]<TextView
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:gravity="center"
        android:background="#f00"
        android:textColor="@android:color/white"
        android:text="hello Android"/>[/tex]






















<TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="hello Android"/>


开写前的一堆废话

Android中的常用控件有很多。比如按钮Button，可以显示一段文本的TextView， 输入框EditText，显示图片ImageView，图片按钮ImageButton进度条ProgressBar，
单选按钮RadioButton，多选框CheckBox，滚动条ScrollView等等，它们都是Android中的常用控件。

View和 ViewGroup区别和联系
Android中的控件绝大多数都是直接继承自View和ViewGroup的，同时ViewGroup又继承自View，所以，Android中一些常用的控件都是直接或者间接的继承自View的。
所以这里也可以说View是TextView和ImageView的父类。Android里面的所有图形界面都是由View和ViewGroup组成的。
1. View
所有可视化控件的父类，提供组件描绘和时间处理方法。
2. ViewGroup
View类的子类，可以拥有子控件,可以看作是容器。Android UI中的控件其实和数据结构中的树有着相似的概念。都是按照层次结构堆叠而成。而创建UI布局的方式有两种：
使用Java代码实现或者通过XML定义布局！后者显得更加方便和容易理解，也是我们最常用的手段。下面我们用一副图来直观的理解一下View，ViewGroup以及他们的子类之间的关系。

我们一般很少直接用View和ViewGroup来写布局，更多的时候使用它们的子类控件或容器来构建布局！

接下来说一下TextView和ImageView的具体用法。
TextView
TextView 可以说是Andriod中最简单的控件了，我们知道它的作用是在界面上显示一条文本信息。下面我们先来看看TextView的简单用法。
<textview
        android:gravity="center" 
        android:background="#f00" 
        android:textColor="@android:color/black" 
        android:textSize="18sp" 
        android:layout_width="200dp" 
        android:layout_height="200dp" 
       android:textStyle="bold|italic" 
       android:id="@+id/tv_text_view" 
       android:text="TextView文字控件"> 
                
上面是一个很普通的TextView控件，我们依次解释一下上面这个TextView的属性。注意：控件的属性是以key-value的形式存在的，前面代表属性名，后面代表属性值。
1. 表示TextView控件的宽度，我们这里赋值为“200dp”
android:layout_width="200dp"
2. 表示TextView控件的高度，我们这里同样也赋值为“200dp”
android:layout_height="200dp"
        注意：Android中的每一个控件都必须有宽度和高度的属性。
        宽高属性分别有三种可选值：match_parent（fill_parent）， wrap_content, "**dp"
        1、其中match_parent代表的是和父容器一样宽，如果你给一个TextView的宽高设置了match_parent，那么这个TextView将会和父容器重叠。
        fill_parent和match_parent是一个意思，只不过fill_parent在之前就已经被废弃了，google推荐我们使用match_parent。

        2、wrap_content代表的是包裹自身的内容。TextView自身文字占多大空间，wrap_content就会表现出刚好包裹住TextView文字的大小。
        3、“**dp” 这个是代表自定义的大小，比如我上面所写的就是自定义的宽高。注意，不要写的太大，这样没什么实际意义。
3. 
android:textColor=“#f00” 

 文字显示颜色，后面的属性值可以是十六进制写法，也可以引用资源文件夹下的color.xml中的属性，我这里是十六进制的简写。
4.文字大小，单位通常是sp。
 android:textSize="18sp"
5. 文字样式。同样有三种可选值：bold代表粗体， italic代表斜体，还有一种是normal，也就是正常显示。通常我们不写这个属性，默认都是normal的。
   如果你想TextView中的文字既是粗体，又是斜体。可以用“属性名|属性名”的方式实现。
android:textStyle="bold|italic"
6. 控件Id。相当于给这个控件取了一个名称，方便我们在Activity（活动）里面通过findViewById（）方法拿到这个控件。
android:id="@+id/tv_textview"
7. 这个就是TextView显示在界面上的文字。
android:text="TextView文字控件"
8. 文字居中显示。
android:gravity="center" 
9. 点击事件响应方法（这个你可能不是很明白，没关系，等到我们讲到控件的点击事件后，你就明白了）
android:onClick="click" 
基本上TextView的常用属性就这些，当然，TextView肯定不仅仅只是这一点属性。它的属性还有很多，比如“android:textAllCaps="true"”这个属性，我们知道是控制显示字母大小写的问题，
但是这个属性在日常的开发中很少用到，所以就直接忽略了，其他属性也是一样。如果你们有兴趣，可以自行去官网了解一下TextView的其他属性。下面我们看一下运行的截图，写了这么久，
肯定要看到一点成果才能鼓励我们继续摸索下去。


上面的太简单了，觉得还不过瘾？那我们来玩一下比普通高级一点的TextView

TextView（s版）
上面的TextView实在是太普通了，如果我想我的TextView酷炫一点？那么我们应该怎么办呢？先来加个阴影试试吧。我们不新建项目了，在原来的TextView中加上如下四条属性。

android:shadowColor="#0ff" 
android:shadowDx="5.0" // 横坐标方向阴影偏离多远
android:shadowDy="5.0" // 纵坐标方向阴影偏离多远
android:shadowRadius="10.0" //阴影的半径

1.  文字阴影的颜色
android:shadowColor="#0ff"    
2. 阴影往x轴方向偏离多远，也就是横坐标方向阴影偏离多远
android:shadowDx="5.0"
3. 阴影往y轴方向偏离多远，也就是纵坐标方向阴影偏离多远
android:shadowDy="5.0"
4. 阴影的半径
android:shadowRadius="10.0"
加了如上四条属性后，我们酷炫的TextView就出来了，当当当当，闪亮登场。我们看一下效果。



什么？还是不够酷炫？ 莫慌，我们的TextView还可以变身。

TextView（plus版）
“你看你看，别人的TextView都可以带圆角的，你这是什么玩意？丑死了”。“嗯，你慌什么慌。这效果我们也是可以做出来的”。

其实圆角的TextView在android中是很容易实现的，我们主要从背景下手。之前我们定义的一个TextView的background属性是用十六进制的背景颜色来表示的，现在我们在background上做一下文章
在res目录下新建一个drawable文件夹。在文件夹中新建一个textview_shape_bg.xml。“哦，我明白了，我们可以自己画一个圆角矩形，把圆角矩形作为TextView的背景颜色设置上去”，“哎哟，不错不错，孺子可教也。”
下面我们在textview_shape_bg.xml文件中添加如下内容

<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle">
	    <corners android:radius="5dp"/>
	    <solid android:color="#f00"/>
	    <stroke android:color="#0f0" android:width="5dp"/>
</shape>	
1. 我们自己定义了这个xml文件显示是一个长方形，他还有其他的可选值，例如line（直线） oval（圆）等等。
android:shape="rectangle"
2. 这里设置了长方形的四个角的圆角半径是5dp
<corners android:radius="5dp"/>
3. 这里设置了长方形的背景颜色
<solid android:color="#f00"/>
4. 设置长方形边框线条的颜色以及边框线条的宽度
<stroke android:color="#0f0" android:width="5dp"/>
暂时先说这一些属性，或许这些你们还不能理解，没关系，我们看看效果，你们对比着效果来看，应该就知道是怎么回事了。



到这里我想你们应该能够理解上面的属性意义了吧？

“哎哎哎，你这TextView光秃秃的，别人的TextView上下左右还能显示图片呢？”，“这.....小意思，毫无难度”。

TextView（plus ++版）

如果我们想在TextView的周围显示图片，这也是很简单的。我们可以在TextView的属性中加入如下代码

android:drawableLeft="@mipmap/ic_launcher"
android:drawableRight="@mipmap/ic_launcher"
android:drawableTop="@mipmap/ic_launcher"
android:drawableBottom="@mipmap/ic_launcher"
咳咳咳咳，我不解释你们知道是什么意思吗？稍微有点英文基础的话，我想这四个属性的含义应该不难吧？

1. 在文字的左边放置如下一张图片
android:drawableLeft="@mipmap/ic_launcher"  
2. 在文字右边放置如下一张图片
android:drawableRight="@mipmap/ic_launcher"
3. 在文字的上边放置如下一张图片
android:drawableTop="@mipmap/ic_launcher"
4.  在文字的下边放置如下一张图片
android:drawableBottom="@mipmap/ic_launcher"
5.  顾名思义，就是文字和图片之间的距离为4dp
android:drawablePadding="4dp"




其他属性

1.单行显示 
android:singleLine="true"   多余的数字用省略号代替
android:maxLines="1"  这个属性也是单行显示，但是多出来的数字不会用省略号代替，该属性可以显示多行
2.水平跑马灯效果
 都去过红灯区吧？知道霓虹灯吧？跑马灯就和霓虹灯差不多，老司机我就不解释了。
android:ellipsize="marquee" //跑马灯
android:singleLine="true" // 单行，如果是多行，无法显示跑马灯效果
android:marqueeRepeatLimit="marquee_forever" //重复次数： 无限重复
3. android:ellipsize还有一些其他的属性：
android:ellipsize="start"      // 省略号从头开始
android:ellipsize="middle"   // 省略号从中间开始 
android:ellipsize="end"   // 省略号从末尾开始 
android:ellipsize="marquee"   // 水平滚动跑马灯效果
android:ellipsize="none"    // 没有任何效果

最后
好了，基本上TextView就这一点东西，被我说完了。其他属性如果有需要的话，自己去查一下文档吧？”哎，不是说了还有一个ImageView要讲的么？“
送你一本”鸟哥语录“吧。举一反三，TextView是显示文字的，ImageView你说他是干嘛的？”瞅我干啥？自己去查呀“。

