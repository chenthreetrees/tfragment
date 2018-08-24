# tfragment

## BaseFragment

封装了懒加载函数，意味着只有在页面可见的时候才加载数据，这种情况在使用viewpager的时候经常遇到。

**使用**：继承该类，重写需要的函数

`getLayoutId()` 返回页面的layout

是否缓存整个页面:fragment在切换的时候，不需要重新创建页面
```
    protected boolean isHoldView()
    {
        return false;
    }
```

是否懒加载:只有在页面可见的时候才加载数据
```
	protected boolean isLazyLoad()
    {
        return false;
    }
```

页面初始化：返回值true-页面可见时候每次都会设置页面，返回值false-页面可见时，只设置一次数据
```
	protected boolean initView(View view)
    {
        return true;
    }
```

页面初始化完成:有些时候，需要在页面初始化完成后处理一些逻辑
```
    protected void onViewInitFinished()
    {

    }
```

页面切换时候，是否重新加载数据:
true-页面可见时候每次都会加载数据，false-页面可见时，只加载一次数据
```
	protected boolean loadData() {
        return true;
    }
```

## ImmersionFragment

将沉浸式状态栏封装到fragment里面，方便开发
android 4.4以上沉浸式状态栏和沉浸式导航栏管理，包括状态栏字体颜色，
一句代码轻松实现，以及对bar的其他设置，[使用说明](https://github.com/gyf-dev/ImmersionBar)。

**使用**：继承该类，重写需要的函数
是否在Fragment使用沉浸式:
```
	protected boolean immersionBarEnabled() {
        return false;
    }
```

初始化沉浸式:根据需求设置样式，以下是默认配置
```
	protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(mActivity);
        mImmersionBar.init();
    }
```

