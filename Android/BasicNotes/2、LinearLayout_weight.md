# 写在前面

如果你是新手，我建议你坚持看下去，因为你肯定不了解权重，而权重并没有想象中的那么简单，希望看完后对你有一定的帮助。

关于说什么专业不专业，或者 App 的流畅性，其实不存在这回事情（我也不清楚，即使有，这也绝对不会是主要的原因）。设置成 0dp，wrap\_content，以及 match_parent 是有很多道理可循的。

如果你是老手，那算了吧。直接跳过这篇文章！我这 lowbee 的文章就不在这里献丑了。

# 关于布局


上节中我们讲了一些 TextView 的基本用法，那么这节我们就来讲一些 Android 中的常见布局之一线性布局 LinearLayout 。

Android早期主要有六大布局：分别是线性布局（LinearLayout），相对布局（RelativeLayout），帧布局（FrameLayout），绝对布局（AbsoluteLayout），表格布局（TableLayout）以及网格布局（GridLayout），随着 Android 的逐步发展，Android 的布局也在随之增多，例如百分比布局（PercentLayout）以及约束布局（ConstraintLayout）等等。

今天我们主要讲解 LinearLayout 的常见用法，先来一张超大的思维导图。

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-20%2010.35.03.png" width="720" height="480">

根据上面的思维导图来看，上面的部分属性我们在之前的 TextView 中已经讲过了，所以在本节中我们主要讲一些 LinearLayout 的特有属性。


# LinearLayout篇

## 常见属性讲解
1. layout\_width： 不过多说了，控件宽度，必要属性。
2. layout\_height： 同上，控件高度，必要属性。
3. id： 定义资源 id ，以方便在 Java 源文件中通过 findViewById(R.id.xx) 生成该控件的实例。
4. orientation： LinearLayout 中的控件排列方向。两种选择：垂直（vertical）或者水平（horizontal）, 反正劳资想怎么摆就怎么摆。

比如你可以这样摆：(水平方向)

<img src ="http://obyg3yq9k.bkt.clouddn.com/1.png" width="360" height="240">

你可以这样摆：（竖直方向）

<img src ="http://obyg3yq9k.bkt.clouddn.com/2017-03-19%2013.59.06.png" width="360" height="480">


“ 咦， 我的第二个 TextView 呢？怎么不见了？”
仔细看一下，TextView001 的宽度填充了屏幕的宽高，你肯定是把 TextView001 的 height 属性设置成了 match\_parent(fill\_parent)。“ 劳资打破你的钛合金头，上节课不是跟你讲了吗？再检查看看。”

“ 哎哟，果然是这样，赶紧换成 wrap\_content 或者自定义标签，比如 200dp 。啊哈，好了。”

<img src ="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-19%2014.10.28.png" width="360" height="240">

5. background：给控件设置背景颜色，这个我们上节已经讲过。

剩下还有两个属性：layout\_gravity 以及 gravity ，这个我们暂时不讲，留到后面再讲。

## 权重讲解
 
 **1.关于权重最基本的用法**
 
 我们首先还是先来看一下实现的效果
 
 <img src="http://obyg3yq9k.bkt.clouddn.com/1.png" width="360" height="480">
 
 
 实现代码：

	<LinearLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:orientation="horizontal"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    tools:context="com.selfassu.layoutdemo.MainActivity">
	    <TextView
	        android:layout_weight="1"
	        android:layout_width="0dp"
	        android:background="#f00"
	        android:layout_height="match_parent"
	        android:text="LinearLayout爸爸，把你的位置分给我一半"/>
	    <TextView
	        android:background="#0f0"
	        android:layout_weight="1"
	        android:layout_width="0dp"
	        android:layout_height="match_parent"
	        android:text="我也要，我也要"/>
	</LinearLayout>
 
 这个就是平分的效果！现在老大（红色）对老二（绿色）说：“ 你个小崽子，你比我小，我应该比你分得多。” 然后就有了下面的效果。来来来，我们来上图。（我就是图，听说你们谁要上我？）
 
 
 <img src="http://obyg3yq9k.bkt.clouddn.com/2.png" width="360" height="480">
 
 实现代码：
 
	 <LinearLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:orientation="horizontal"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    tools:context="com.selfassu.layoutdemo.MainActivity">
	    <TextView
	        android:layout_weight="2"
	        android:layout_width="0dp"
	        android:background="#f00"
	        android:layout_height="match_parent"
	        android:text="LinearLayout爸爸，把你的位置分给我一半"/>
	    <TextView
	        android:background="#0f0"
	        android:layout_weight="1"
	        android:layout_width="0dp"
	        android:layout_height="match_parent"
	        android:text="我也要，我也要"/>
	</LinearLayout>
	
	
肯定有人说，你这代码都是一样的，你个傻吊，代码贴两遍。嗯？不忙，你仔细看看图，真的是一样的嘛？结果逐行对比一看，你会发现上面的两个例子中，只有一行代码是有区别的。在红色的 TextView 
中出现了这样的代码，如下：

	第一张图片中的红色区域
	android:layout_weight="1"
	第二张图中的红色区域
	android:layout_weight="2"
	
就一个 “1” 和 “2” 的区别，为什么会这样呢？

首先，我们讲一下第一个图中是怎么来的，根据图的效果，我们可以看到老大（红色）和老二（绿色）平分了屏幕的宽度，我们分别看一下两个TextView 中是如何定义的？逐行对比你会发现，他们两个中都定义了

	android:layout_weight = "1" 
	
这个属性。

“ 难道是？”，

“ 嗯，悟性还不错！没错，就是这样。因为他们每个中都定义了 weight 属性的值为 1，所以总的份数就是 1 + 1 = 2（有两个 TextView 有 weight 为 1 的属性），而他们每个 weight 定义都为 1，所以单个 TextView 的占总数比恰好等于 1 / 2，老大占 1 / 2，老二也占 1 / 2，这与我们上面的第一个图恰好吻合，所以它们的一半一半就是这么来的！”

知道了第一张图的根本原因，那么我想第二个图也就不难解释了。总的份数是 1 + 2 = 3，老大（红色）占比 2 / 3， 老二（绿色）占比 1 / 3。不难吧，如果不难的话，我们进入下面的学习。（ps：竖直方向你们就自己去折腾，和水平方向一样一样的）

**2. weight 属性的详解**

“ 上面的权重情况很好理解吧？”

“ 何止是简单，简直简单的不像话？这属性有什么可说的？你这完全是没必要讲，正常人都能够理解。”

“ 嗯，好的。你这只是片面的理解。那我们接下来看看你口中所谓的简单权重的另外一种情况！”

不知道你注意没有，在水平方向平分的情况下，上面的情况我们都只是设置了 TextView 的宽度全部都为 0dp 的情况下，不知道你考虑过一个问题没有，如果我们将 TextView 的高度不设置成 0dp ，设置成 wrap\_content ，match\_parent 或者自定义的情况，那结果还是那样吗？

	注意: 当我们在水平方向上使用 weight 属性的时候，控件所占屏幕比和控件的高度本身没有任何关系，只是和高度的值有关系。
	
“ 你说的是不是真的呀，我怎么觉得你是个假的大佬？”

“ 嗯，不信，没关系，俗话说：Talk is cheap, show me the code! ”

下面我们来看一段代码

	<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.selfassu.layoutdemo.MainActivity">
	    <TextView
	        android:layout_weight="1"
	        android:layout_width="wrap_content"
	        android:background="#f00"
	        android:layout_height="match_parent"
	        android:text="总的份数是3， 我的权重为1，所以我占总分数的 1/3"/>
	    <TextView
	        android:layout_weight="1"
	        android:background="#0f0"
	        android:layout_width="wrap_content"
	        android:layout_height="match_parent"
	        android:text="总的份数是3， 我的权重为1，所以我占总分数的 1/3"/>
	    <TextView
	        android:layout_weight="1"
	        android:background="#00f"
	        android:layout_width="wrap_content"
	        android:layout_height="match_parent"
	        android:text="总的份数是3， 我的权重为1，所以我占总分数的 1/3"/>
	</LinearLayout>

总的份数是 3 份，里面有 3 个 TextView ，每个 TextView 的权重都为 1 ，这里我们将 TextView 的高度设置成了 wrap\_content 。下面我们看看效果图。

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-19%2015.09.32.png" width="360" height="480">

显然和上面推断一样，正常的不能再正常了。好，那么我们将高度换成 “ match\_parent ” 试试。

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-19%2015.09.32.png" width="360" height="480">

吃鲸，也一样。

“ 你真的不是一个假的大佬么？轻蔑脸.png，玩蛋去吧，傻吊，我懒得陪你了。”

“ 慢着，你再看看下面的这段代码？”

	<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.selfassu.layoutdemo.MainActivity">
	    <TextView
	        android:layout_weight="1"
	        android:layout_width="match_parent"
	        android:background="#f00"
	        android:layout_height="match_parent"
	        android:text="总的份数是6， 我的权重为1，所以我占总分数的 1/6"/>
	    <TextView
	        android:layout_weight="2"
	        android:background="#0f0"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:text="总的份数是6， 我的权重为2，所以我占总分数的 2/6"/>
	    <TextView
	        android:layout_weight="3"
	        android:background="#00f"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:text="总的份数是3， 我的权重为3，所以我占总分数的 3/6"/>
	</LinearLayout>

“ 这么简单的问题，你别再烦我了，行不行。lowbee ？红色占 1 / 6，绿色占 2 / 6，蓝色占3 / 6。”

“ 你确定吗？注意我们的宽度不再是 0dp 了，而是 match\_parent 了！！！”

“ 这有什么不确定的？你傻我可不傻。哼！！！”

我们看看图：

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-19%2015.22.05.png" width="360" height="480">


咦，明明是 1：2：3 的比例，怎么会出现这样的结果？蓝色部分去哪里了？代码里面明明还有一个蓝色的部分的呀，而且蓝色部分占的比例最高，怎么会一点都没有了？原本 1：2：3的比例现在却变成了 2：1：0 。为什么会出现这样的情况呢？其实权重没有那么简单的。当我们水平方向上设置权重的时候并且高度设置成 match\_parent 的时候，权重会出现**“反比例解析”**的问题。

”别再自以为是了，你以为你真的了解权重么？“

所以，权重没你想的那么简单！在水平方向上使用权重，还是要分情况进行计算的，他们分别都有自己对应的公式。首先我们讨论一下 wrap\_content 和 match\_parent 两种情况：（以下举例都是在水平方向使用权重，也就是改变控件的宽度情况下）
	
	
> 1. 当控件的宽度设置成 wrap\_content 的情况下，也就是 android：layout\_width = “wrap\_content” 的情况下：

> 		控件的宽度 = （（控件的宽度 - 所有控件的宽度和）* 控件所占的权重） / 总的权重数  + 自身的宽度
	
> 2. 当控件的宽度设置成 match_parent 的情况下，也就是android：layout\_width = “match\_parent” 的情况下：

>		控件的宽度 = （（控件的宽度 - 所有控件的宽度和） * 控件所占的权重） / 总的权重数 + 自身的宽度
	
举个例子吧。利用一下数学知识，假设宽度为 x ， 里面有三个 TextView ，他们的宽度也分别为 x ，
那么 weight 为 1 的 TextView 实际占比例为：

	y = （(x - 3x) * 1）/6  + x = 2x/3

接着我们计算 weight 为 2 的 TextView 实际占比为：

	y = （（x - 3x） * 2）/6 + x = 1x/3;

他们两者加起来就已经等于了 x ，而 x 正好是屏幕的宽度，所以第三个权重占比为 3 的蓝色TextView 自然也就不会展示出来了。而红色 TextView 和绿色 TextView 的实际比例却是 2：1 ，这也与我们上面的图片结论相吻合。


关于权重，不同的写法会有不同的效果，例如我们刚才只是讨论了控件相同的情况，不知道你想过没有，如果控件不同呢？又是一个什么情况？比如下面的例子？

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-20%2009.48.10.png" width="80%" height="50%">

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-20%2009.48.54.png" width="80%" height="50%">

你们应该都能够看出来这绝对不是平分的效果，至于为什么，我提示一下：

	在控件不同，实际上 LinearLayout 是分两个步骤来设置视图宽度的。
	第一步，LinearLayout 会查看 layout_width 属性值。
	第二步，LinearLayout 才会依据 layout_weight 属性值进行额外的空间分配。


还有一些情况，需要你们自己去摸索，我就不在这里说了，毕竟篇幅太多了，我相信能够看到这里的人并不多。所以这里就不展开了。



## 分割线

你们肯定在实际情况中，经常会看到这样的界面？

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-20%2009.54.48.png" width="360" height="50%">

细心的你观察到界面的变化没有，我们姑且称每一个条目为一个 item ，那么每一个 item 下面都有一条对应的分割线，是不是？不要跟我说你没看到，你瞎呀你？没看到？这么明显。至于这个分割线是怎么做出来的呢？我这里可以说有很多种实现该分割线的办法。

1. 比如用图片，图片可以是一条分割线。
2. 比如用 layout\_marginTop = 1dp 或者 layout\_margin = 0.5dp 的方式。
3. 比如用 shape.xml 。
4. 比如用 LinearLayout 的 divider 。
5. 比如用 LinearLayoutCompat 的 divider 。

这都是办法，只要你想做，一定是可以实现的。但是今天我们主要讲 divider ，所以我们这里就不详细说如下的几种情况了，只是单纯说一下 LinearLayout 的分割线如何使用。

很简单的使用，不详细展开了，直接上代码。

	<LinearLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:orientation="vertical"
	    android:divider="@color/colorAccent"
	    android:showDividers="beginning|middle|end"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    tools:context="com.selfassu.layoutdemo.MainActivity">
	    <TextView
	        android:layout_marginTop="10dp"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:text="我是你大爷"/>
	    <TextView
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:text="我是"/>
	    <TextView
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:text="我是"/>
	    <ImageView
	        android:background="@mipmap/ic_launcher"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"/>
    </LinearLayout>
    	

效果图嘛，看这里。

4.1 的效果
	
<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-20%2010.09.45.png" width="50%" height="80%">

但是你会发现一个坑爹的情况，就是 5.0 以上的系统这样设置并没有什么卵用，它没有分割线！！！
我知道没有图片你们不会相信的！！！ 6.0 以上也没有，不信你去试试。那么这个时候你应该考虑用其他方法了，或者使用 LinearLayout 的兼容类 LinearLayoutCompat。

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-20%2010.09.56.png" width="50%" height="80%">

**android:dividerPadding="10dp"**

这个我就不说了，你们看图也都能够看懂，就是分割线和左右的边距。

	<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:divider="@color/colorAccent"
    android:showDividers="beginning|middle|end"
    android:dividerPadding="10dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.selfassu.layoutdemo.MainActivity">
		<TextView
		    android:layout_marginTop="10dp"
		    android:layout_width="match_parent"
		    android:layout_height="wrap_content"
		    android:text="我是你大爷"/>
		<TextView
		    android:layout_width="match_parent"
		    android:layout_height="wrap_content"
		    android:text="我是"/>
		<TextView
		    android:layout_width="match_parent"
		    android:layout_height="wrap_content"
		    android:text="我是"/>
		<ImageView
		    android:background="@mipmap/ic_launcher"
		    android:layout_width="match_parent"
		    android:layout_height="wrap_content"/>
    </LinearLayout>

<img src="http://obyg3yq9k.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-03-20%2010.21.26.png" width="50%" height="80%">

## LinearLayoutCompat 
这个东西就是 LinearLayout 的兼容类，上面我们不是说了 5.0 以上没有分割线的效果么？不妨你试试它？会让你有意想不到的惊喜！（ ps：这玩意和 LinearLayout 的分割线用法一样！）

	“ 什么？你问我这玩意怎么用？”
	“ 查文档去吧！”
	“ 什么？你看不懂英文？”
	“ 去google！”
	“ 不能翻墙。。。”
	“ 去百度！”
	你再说不会百度，我建议你别学编程了，因为你不适合。

最后说一句，学编程的路上困难总是很多，但是只要你认定一个理，坚持下去，我相信困难总比方法多。呸呸呸，**方法总比困难多！** 	










 
 