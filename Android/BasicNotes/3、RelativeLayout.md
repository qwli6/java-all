# 开车
前面大致介绍了 Android 中的一些布局。Android 中的布局有点多，而极其常用的就线性布局 LinearLayout，相对布局 RelativeLayout，以及 FrameLayout 这三种布局出现的比较频繁，由于 LinearLayout 布局中关于 weight（权重）这一部分讲解的比较多，所以占用了大量的篇幅，所以在上一篇文章中便没有

好了，不废话了，直接开车。

先列个提纲吧。顺便把上一篇 LinearLayout 里面遗留的一些问题解决掉。

> 1. RelativeLayout的常见用法。
> 2. layout_gravity 和 gravity 的区别。
> 3. padding 和 margin 的区别。

# RelativeLayout

老规矩，还是先来一张相对布局的思维导图吧。

### 关于父容器定位
用一张比较简单的图片来描述一下。我们看一下下面的一张图片。



### 关于兄弟组件定位



一看，懵逼了。怎么这么多 xml 属性？仔细观察一下，其实很多属性都是相对应的，比如 android:layout_centerVertical 和 android:layout_centerHorizontal 这两个就是相对应的，至于这两个属性是什么意思？根据字面翻译我想我们很容易就可以猜出这两个属性一个是水平居中，一个是垂直居中，另外我们可以再观察一下，有很大部分属性都是以 left，right，bottom，top 结尾的，所以这些属性也都是相对的。每个属性的意思我就不再单独讲解了，我们直接根据思维导图来看看它们那些属性在代码中的应用。

