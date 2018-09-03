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

该类继承至BaseFragment
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

## RecyclerFragment

该类继承至ImmersionFragment
将整个页面封装成列表页面，配合BaseRecyclerAdapter使用，
使用MultTypeAdapter，实现多类型子项的适配，使用简单，提供可定制的titlebar和无数据时显示页面
页面布局采用FrameLayout

**使用**：继承该类，重写需要的函数
设置recyclerview是否需要下拉刷新：
```
protected boolean isRefresh()
```

设置recyclerview是否需要上拉加载更多:
```
protected boolean isLoadmore()
```

设置布局模式,默认使用LinearLayoutManager:
```
protected RecyclerView.LayoutManager getLayoutManager()
```

设置空白数据页面:
```
protected View getEmptyView()
```

设置Titlebar：
```
protected View getTitleBar()
```

设置Titlebar的高度，由于页面布局采用framelayout，所以需要指定高度：
```
protected int getTitleBarHeight()
```

设置适配器:
```
protected abstract BaseRecyclerAdapter getAdapter();
```

**在getAdapter()方法里面，适合去做一些recyclerview的设置**

添加ItemDecoration,该框架提供了若干Decoration:
```
public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration)
```

设置上拉加载事件:
```
public void setOnLoadMoreListener(HTLoadMoreListener listener)
```

设置下拉刷新事件:
```
public void setOnRefreshListener(HTRefreshListener listener)
```

设置是否显示无更多加载：
该方法在setRefreshCompleted（false）时候起作用
true-显示无更多加载，false-隐藏没有更多，每次上拉有隐藏的动画效果
```
public void setLoadMoreViewShow(boolean isShow)
```


**其他公有方法**

结束刷新:
false-没有更多数据可加载，下次将不会回调onloadmore
```
public void setRefreshCompleted(boolean hasMore)
```

显示空白数据页面:
```
public void showEmptyView()
```

隐藏空白数据页面:
```
public void hideEmptyView()
```

## MultTypeAdapter

**使用**：只需要将数据类型和viewBinder注册即可，详细阅读源码的实现

```
final MultTypeAdapter adapter = new MultTypeAdapter(mActivity,list);
        adapter.registerLayout(MainItem.class, new ViewBinder() {
            @Override
            public int getLayoutId()
            {
                return R.layout.item_text;
            }

            @Override
            public void bindData(BaseRecyclerHolder holder, Object item, int position)
            {
                MainItem mainItem = (MainItem)item;
                TextView textView = holder.getView(R.id.content_tv);
                textView.setText(mainItem.text);
            }
        });

        adapter.registerLayout(ImageItem.class, new ViewBinder() {
            @Override
            public int getLayoutId()
            {
                return R.layout.item_button;
            }

            @Override
            public void bindData(BaseRecyclerHolder holder, Object item, int position)
            {
                ImageItem mainItem = (ImageItem)item;
                Button button = holder.getView(R.id.button);
                button.setText(mainItem.text);
            }
        });
```

## ItemDecoration

**使用**：提供分割线ItemDecoration，详细阅读源码的实现，使用如下：
```
		DividerDecoration dividerDecoration = new DividerDecoration.Builder()
                .left(12)
                .right(12)
                .height(2)
                .color(Color.RED)
                .build();
        PaddingDecoration paddingDecoration = new PaddingDecoration.Builder()
                .left(12)
                .right(12)
                .build();
        addItemDecoration(paddingDecoration);
        addItemDecoration(dividerDecoration);
```


