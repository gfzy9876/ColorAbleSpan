# ColorAbleSpan
仿微博/微信朋友圈文字点击变色实现：

![img](https://github.com/gfzy9876/ColorAbleSpan/blob/master/previews/preview1.png)

![img](https://github.com/gfzy9876/ColorAbleSpan/blob/master/previews/preview2.png)

使用如下：

* 添加依赖：[![](https://jitpack.io/v/gfzy9876/ColorAbleSpan.svg)](https://jitpack.io/#gfzy9876/ColorAbleSpan)

```groovy
dependencies {
  	implementation 'com.github.gfzy9876:ColorAbleSpan:Tag'
}
```

* 创建布局：

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <pers.zy.colorable_lib.ColorAbleTextView
        android:id="@+id/tv_color_able"
        android:layout_width="wrap_content"
        android:text="#今天发一张最美自拍照#我能亲口跟你说早安吗，先亲口再早安"
        android:textSize="20sp"
        android:layout_marginTop="12dp"
        android:textColor="@android:color/black"
        app:pressed_color="@color/colorAccent"     //按下去文字颜色
        app:normal_color="@color/colorPrimaryDark"   //不按下去文字颜色
        android:layout_height="wrap_content"/>

</FrameLayout>
```

* 样式以及点击效果设置：

```kotlin
//前两个参数为设置span的范围，第三个是点击事件处理。
tv_color_able.setColorAbleClickListener(0, 12, object : ColorAbleTextView.ColorClickListener {
  	override fun onClick(widget: View) {
    Toast.makeText(this@MainActivity, "点了", Toast.LENGTH_SHORT).show()
  }
})
tv_color_able.setColorPressedMode(ColorAbleSpanMethod.MODE_ROUND)  //点击背景变色
tv_color_able.setColorPressedMode(ColorAbleSpanMethod.MODE_TEXT)		//点击文字变色
```