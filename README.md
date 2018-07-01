# 自定义无压缩加载超清大图
##前言
已经很久没有写博客了，前段时间做项目就遇到加载超大图时系统内存溢出，我们一般处理加载图片时OOM的方法都是对图片进行压缩。但是发现手机系统相册是可以打开大图的，今天就分享一波自定义无压缩加载超清大图。
![图片](https://github.com/Terrybthvi/BigPictureLoading/blob/master/image/ezgif-1-a99a439919.gif)
##BitmapRegionDecoder
　　`BitmapRegionDecoder`用来解码一张图片的某个矩形区域，通常用于加载某个图片的指定区域。通过调用该类提供的一系列`newInstance(...)`方法可获得`BitmapRegionDecoder`对象，该类提供的主要构造方法如下：
![图片](https://github.com/Terrybthvi/BigPictureLoading/blob/master/image/21A412B8-00E3-4F33-A954-03E14D134AA7.png)
获取该对象后我们可以通过`decodeRegion(rect,mOptions)`方法传入需要显示的指定区域，就可以得到指定区域的`Bitmap`。这个方法的第一个参数就是要显示的矩形区域，第二个参数是`BitmapFactory.Options`(这个
类是BitmapFactory对图片进行解码时使用的一个配置参数类，其中定义了一系列的public成员变量，每个成员变量代表一个配置参数。)