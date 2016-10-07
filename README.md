## 前言 ##
纵观android市面上的所有APP，没有一个页面不具备欢迎引导页，可以看出引导页面的魅力有多大，引导页面能迅速抓住用户的眼球，让用户很快的了解该app的主打方向。一个好的引导页让人看了就喜欢，甚至于达到卸载重装看引导页的地步。那么，市面上的app引导页都是用什么做的呢。我大致分为3类。
### 1. 普通的viewpager页面 ###
这是最普通的一种了，很多app就是这个，这个在刚开始流行的时候，可能还会感到新奇，但是，这么长时间了，人们都形成审美疲劳了（不管是开发人员还是用户，还是...）
### 2. 视差引导页面 ###
这种比上一种强点，但是好像用的不多，为什么呢，因为下面第三种。这个动画看起来很炫酷，很牛B，但是越炫酷越难实现（相比较html5来说）。
### 3. HTML5引导页面 ###
现在越来越多的app开始使用这种方式，HTML5在动画方面是很强大的，所以能弄出来很牛的效果。


----------
好了，废话不多说，开始今天的学习之旅。。。
## 普通的ViewPager引导页面 ##
先来张效果图看看。
![](http://img.blog.csdn.net/20151127080626896),
这里只是也演示的demo，为了省事，我就没加指示器。那么，接下来我们就说下具体的实现方法。很简单（ViewPager已经被玩烂了有木有。）

### 1. 在xml文件中添加 ###

```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.gl.NormalViewPager">

    <android.support.v4.view.ViewPager
        android:id="@+id/normalviewpager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</RelativeLayout>
```
### 2. 编写Adapter类 ###
因为这里没有其他元素，我们就只是添加点ImageView进来。所以代码简单点。

```
public class NormalPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> mDatas;

    public NormalPagerAdapter(Context context,List<ImageView> mDatas){
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((ImageView) object);
    }


    @Override
    public ImageView instantiateItem(ViewGroup container, int position) {
        container.addView(mDatas.get(position));
        return mDatas.get(position);
    }

}
```
viewpager都被玩烂了，上面的代码也没啥可说的，
### 3.初始化ViewPager，初始化视图数据、添加监听器，等等 ###
在这一步我们能干的事就多了

```
private void initData() {
        ImageView imageView_1 = new ImageView(this,null);
        imageView_1.setBackgroundResource(R.drawable.hpw1);
        ImageView imageView_2 = new ImageView(this);
        imageView_2.setBackgroundResource(R.drawable.hpw2);
        ImageView imageView_3 = new ImageView(this);
        imageView_3.setBackgroundResource(R.drawable.hpw3);
        ImageView imageView_4 = new ImageView(this);
        imageView_4.setBackgroundResource(R.drawable.hpw4);
        ImageView imageView_5 = new ImageView(this);
        imageView_5.setBackgroundResource(R.drawable.hpw5);
        mDatas = new ArrayList<>();
        mDatas.add(imageView_1);
        mDatas.add(imageView_2);
        mDatas.add(imageView_3);
        mDatas.add(imageView_4);
        mDatas.add(imageView_5);

    }

    private void initAdapter() {
        adapter = new NormalPagerAdapter(this,mDatas);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("tag","this is offset--->"+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
```
上面的代码就是添加个监听器，添加个切换动画什么的，相信大家还是都会的。关于ViewPager的切换动画，请移步[张鸿洋](http://blog.csdn.net/lmj623565791/article/details/40411921)

## 视差引导页面 ##
在说这个之间，我先吐槽下腾讯课堂某州学院，妈的，大概今年4月份的时候，我去听免费课，他在那里将小红书，我说这个github上就有，怎么能说国内没几个人会呢？然后把链接发在了公屏上，然后。。。我TMD现在还在小黑屋。

先来2张gif图看看。其中一张是ecmobile稍微改了下，另一张是著名的小红书。
![](http://img.blog.csdn.net/20151127082213336)
怎么感觉背景图被打了马赛克呢？不管他了，看另一张。
![](http://img.blog.csdn.net/20151127082406544)
看到没，效果感觉不错吧。那么我们说下原理，原理呢其实也不难，归结为一句话：层级不同，滑动速度不同。
### Ecmobile的实现 ###
#### 1.XML文件 ####
由于XML代码有点多，就不贴出来了，这里给出个层级图。
![](http://img.blog.csdn.net/20151127084225192)
层级为3层
	

 - 背景图层（像是被打了马赛克） back_image_one
 - layer层  内含FrameLayout，一个FrameLayout对应一个ViewPager的pager页面。我们要控制的就是FrameLayout中的元素的滑动速度。
 - ViewPager层

#### 2.Adapter的实现 ####
代码有点长，我贴instantiateItem函数。

```
@Override
    public Object instantiateItem(ViewGroup container, int position) {

        final ViewHolder holder;
        holder = new ViewHolder();
        View imageLayout = mInflater.inflate(R.layout.gallery_image_item, null);

        holder.image = (LinearLayout) imageLayout.findViewById(R.id.gallery_image_item_view);

        if(position == 4) {
            holder.image.setEnabled(true);
        } else {
            holder.image.setEnabled(false);
        }
        if(position == 0) {
            holder.image.removeAllViews();
            View view0 = inflater.inflate(R.layout.lead_a, null);
            holder.image.addView(view0);
        } else if (position == 1) {
            holder.image.removeAllViews();
            View view1 = inflater.inflate(R.layout.lead_b, null);
            holder.image.addView(view1);
        } else if (position == 2) {
            holder.image.removeAllViews();
            View view2 = inflater.inflate(R.layout.lead_c, null);
            holder.image.addView(view2);
        } else if (position == 3) {
            holder.image.removeAllViews();
            View view3 = inflater.inflate(R.layout.lead_d, null);
            holder.image.addView(view3);
        } else if (position == 4) {
            holder.image.removeAllViews();
            View view4 = inflater.inflate(R.layout.lead_e, null);
            holder.image.addView(view4);
        }

        ((ViewPager) container).addView(imageLayout, 0);

        return imageLayout;

    }
```
看看gallery_image_item.xml文件

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#00000000"
    android:orientation="vertical" >

    <FrameLayout
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" >

        <LinearLayout
            android:id="@+id/gallery_image_item_view"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical" >
        </LinearLayout>
    </FrameLayout>

</LinearLayout>
```
这里在结合代码我们知道了，逻辑就是根据position，向这个布局中添加对应的视图（添加之前先要移除原来添加的）。这里要注意，这个布局是透明的，是透明的，是透明的。
看看lead_a
![](http://img.blog.csdn.net/20151127085550867)
,到这里，视图就搞清楚了，那么我们看代码是如何控制速度的。

#### 3.设置，控制 ####
我们在层级树中知道，第二层是一个横向滑动的Framelayout。所以我们需要设置每个FrameLayout的大小。如下代码:

```
FrameLayout.LayoutParams frameLayoutParams;
        ImageView layer_image_one = (ImageView) findViewById(R.id.layer_image_one);
        frameLayoutParams = (FrameLayout.LayoutParams) layer_image_one.getLayoutParams();
        frameLayoutParams.height = dm.heightPixels;
        frameLayoutParams.width = dm.widthPixels;
        layer_image_one.setLayoutParams(frameLayoutParams);
```
其他几个一样。
当然，也需要设置下最下层的背景。

```
ImageView back_image_one = (ImageView) findViewById(R.id.back_image_one);
        layoutParams = back_image_one.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        back_image_one.setLayoutParams(layoutParams);
```
我觉得这里设置一个就OK，因为在这里我们的背景被没有滑动。
最最关键的一点，给viewPager设置监听器，并在onPageScrolled中监听滑动并设置layer层元素的滑动。

```
viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float realOffset = Cubic.easeIn(positionOffset, 0, 1, 1);

                total_page = adapter.getCount();
                float offset = (float) ((float) (position + realOffset) * 1.0 / total_page);
                int offsetPositon = (int) (backgoundWidth * offset);

                float layerRealOffset = Sine.easeIn(positionOffset, 0, 1, 1);
                float layerOffset = (float) ((float) (position + layerRealOffset) * 1.0 / total_page);
                int layerOffsetPositon = (int) (backgoundWidth * layerOffset);
                layer_srcollview.scrollTo(layerOffsetPositon, 0);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
```
看下easeIn

```
public static float  easeIn(float t,float b , float c, float d) {
        return -c * (float)Math.cos(t/d * (Math.PI/2)) + c + b;
    }
```
说下上面的这个玩意。上面那个玩意就是余弦波的3PI/2到2PI的波形状，

```
float layerOffset = (float) ((float) (position + layerRealOffset) * 1.0 / total_page);
```
在+上position([0,1)),layerOffset就也到了0到1范围，波的形状是倒过来的cos波，开始的1/4段，这样就波斜率（对应速度）也就越来越快，直到停止，你们可以画图看下，这样就形成了我们上面图中看到的视差。

### 小红书的实现 ###

先看下目录结构。
![](http://img.blog.csdn.net/20151127102048961)
其中最关键的是parallaxContainer（自定义布局）和parallaxLayoutInflater（加载布局）
![](http://img.blog.csdn.net/20151127102241010)
几个布局页里面都是些ImageView，发现ImageView里面有了新的属性，（这里报红没关系，还是可以编译通过切正常运行）
有哪些属性呢？看下面这个类。（ps，就不看attrs.xml文件了）

```
public class ParallaxViewTag {
    protected int index;
    protected float xIn;
    protected float xOut;
    protected float yIn;
    protected float yOut;
    protected float alphaIn;
    protected float alphaOut;
}
```
工作原理这里就不说了。上面有说到过，那么我们看看小红书是怎么实现的。

```
mParallaxContainer.setImage(iv_man);
                mParallaxContainer.setLooping(false);

                iv_man.setVisibility(View.VISIBLE);
                mParallaxContainer.setupChildren(getLayoutInflater(),
                        R.layout.view_intro_1, R.layout.view_intro_2,
                        R.layout.view_intro_3, R.layout.view_intro_4,
                        R.layout.view_intro_5, R.layout.view_login);
```
设置中间那个人，设置子布局。我们看看setupChildren方法。

```
public void setupChildren(LayoutInflater inflater, int... childIds) {
        if (getChildCount() > 0) {
            throw new RuntimeException("setupChildren should only be called once when ParallaxContainer is empty");
        }

        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(
                inflater, getContext());

        for (int childId : childIds) {
            View view = parallaxLayoutInflater.inflate(childId, this);
            viewlist.add(view);
        }

        pageCount = getChildCount();
        for (int i = 0; i < pageCount; i++) {
            View view = getChildAt(i);
            addParallaxView(view, i);
        }

        updateAdapterCount();

        viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        viewPager.setId(R.id.parallax_pager);
        attachOnPageChangeListener();
        viewPager.setAdapter(adapter);
        addView(viewPager, 0);
    }
```
代码也不难，

 -  将布局页加载进来 

```
View view = parallaxLayoutInflater.inflate(childId, this);
            viewlist.add(view);
addParallaxView(view, i); 
```
 -  初始化ViewPager并设置监听

```
viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        viewPager.setId(R.id.parallax_pager);
        attachOnPageChangeListener();
        viewPager.setAdapter(adapter);
        addView(viewPager, 0);
```

我们看下addParallaxView方法

```
private void addParallaxView(View view, int pageIndex) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; i++) {
                addParallaxView(viewGroup.getChildAt(i), pageIndex);
            }
        }

        ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
        if (tag != null) {
            tag.index = pageIndex;
            parallaxViews.add(view);
        }
    }
```
这个方法中就是用添加子view的。

接着看attachOnPageChangeListener方法。
有点长，就给出关键代码把。就是初始化了ViewPager.OnPageChangeListener监听器，并在onPageScrolled方法中根据我们的tag和偏移量来移动view。

```
for (View view : parallaxViews) {
                    tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }

                    if ((pageIndex == tag.index - 1 || (isLooping && (pageIndex == tag.index
                            - 1 + pageCount)))
                            && containerWidth != 0) {

                        // make visible
                        view.setVisibility(VISIBLE);

                        // slide in from right
                        view.setTranslationX((containerWidth - offsetPixels) * tag.xIn);

                        // slide in from top
                        view.setTranslationY(0 - (containerWidth - offsetPixels) * tag.yIn);

                        // fade in
                        view.setAlpha(1.0f - (containerWidth - offsetPixels) * tag.alphaIn / containerWidth);

                    } else if (pageIndex == tag.index) {

                        // make visible
                        view.setVisibility(VISIBLE);

                        // slide out to left
                        view.setTranslationX(0 - offsetPixels * tag.xOut);

                        // slide out to top
                        view.setTranslationY(0 - offsetPixels * tag.yOut);

                        // fade out
                        view.setAlpha(1.0f - offsetPixels * tag.alphaOut / containerWidth);

                    } else {
                        view.setVisibility(GONE);
                    }
                }
```
那么，这里频繁出现的Tag是什么时候设置的呢。仔细想想，应该是在加载的时候设置的把？那我们去看看setupChildren方法。呀，仔细一看，发现如下代码

```
ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(
                inflater, getContext());

        for (int childId : childIds) {
            View view = parallaxLayoutInflater.inflate(childId, this);
            viewlist.add(view);
        }
```
每个子view都是通过ParallaxLayoutInflater来加载的。那么我们就去看看他.

```
public class ParallaxLayoutInflater extends LayoutInflater {
    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        setUpLayoutFactory();
    }

    private void setUpLayoutFactory() {
        if (!(getFactory() instanceof ParallaxFactory)) {
            setFactory(new ParallaxFactory(this, getFactory()));
        }
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this, newContext);
    }
}
```
返现构造方法中，通过setFactory方法，设置Factory为ParallaxFactory，到这里，就明白了。ParallaxFactory实现了LayoutInflater.Factory，这个接口是干什么用的。我们这个时候去看看这个的介绍
![](http://img.blog.csdn.net/20151127104724582)
大概的意思是可以通过实现这个接口来获取xml文件中的tag（就像小红书中的app：x_in等等），欧，到这里就明白了，由于ImageView不是我们的自定义控件，我们没法在ImageView的构造方法中获取一些值，所以我们通过实现这个接口来获取。

```
protected void onViewCreated(View view, Context context, AttributeSet attrs) {

    int[] attrIds =
        { R.attr.a_in, R.attr.a_out, R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out,  };

    TypedArray a = context.obtainStyledAttributes(attrs, attrIds);

    if (a != null) {
      if (a.length() > 0) {
        ParallaxViewTag tag = new ParallaxViewTag();
        tag.alphaIn = a.getFloat(0, 0f);
        tag.alphaOut = a.getFloat(1, 0f);
        tag.xIn = a.getFloat(2, 0f);
        tag.xOut = a.getFloat(3, 0f);
        tag.yIn = a.getFloat(4, 0f);
        tag.yOut = a.getFloat(5, 0f);
        view.setTag(R.id.parallax_view_tag, tag);
      }
      a.recycle();
    }
  }
```
在这个方法中，我们获取了属性并给view设置了tag，所以，在后面我们就可以通过tag来获取这些值了。
看下Adapter

```
 @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view;
        if (!recycleBin.isEmpty()){
            view = recycleBin.pop();
        }else {
            view = new View(context);
            //这里注意，如果不想加前缀，请以静态的方式将包和属性导进来
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
        container.addView(view);
        return view;
    }
```
恩？什么都没有的空白页？想想也没错，我们的所有逻辑都在parallaxContainer这个自定义Layout里面，图片什么的也都是，所以我们不需要ViewPager有什么东西（这里是个ecmobile那个的区别之一），我们的viewpager只负责滑动，只负责触发onPageScrolled方法，剩下的就是有view（ImageView）的setTranslationX、setTranslationY、setAlpha方法来实现。
有兴趣的去研究下或者去改造下这个吧。[Github地址](https://github.com/w446108264/XhsParallaxWelcome)

## HTML5实现引导页 ##
![](http://img.blog.csdn.net/20151127110633267)
效果确实很炫，native来实现确实难。这张gif图看起来有点卡的原因是我是加载一个微场景。实际项目中会把html5+css3+js代码放在Assest文件夹下面。有图为证
![](http://img.blog.csdn.net/20151127111011056)
，我们来反编译下这个apk，来验证我们。[MAC下反编译APK](http://blog.csdn.net/hanhailong726188/article/details/42368295)，
我们找到如下一行代码。
![](http://img.blog.csdn.net/20151127111443933)，
证明我们是对的。
关于webview就不说了。
## 总结 ##
在这三种引导页中，我更倾向于第三种，必将界面更加酷炫（虽然响应速度不如native），加上现在更加流行hybrid开发（淘宝、天猫、携程、百度糯米等等），我认为在不久的以后，html5引导页将越来越流行。


----------
如果有错误的地方，请指出。

代码地址：[全世界的github](https://github.com/Guolei1130/android-guidedemo)

