# ActionMonitor
#### 用户行为数据采集（按行为轨迹采集）
该版本只有按用户行为轨迹采集数据，用户点击等行为暂时不采集。

#### 用户行为规则
1、A -> B -> A<br>
2、A -> B -> Home<br>
3、B -> C -> A ->...-> B或者Home<br>

#### 添加依赖
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
		compile 'com.github.wshychbydh:ActionMonitor:xx'
	}

#### 使用说明

在Application的onCreate中添加

	MonitorSdk.init(this);
	registerActivityLifecycleCallbacks(new ActivityLifecycleImpl());

>如果需要打印日志，打开日志开关即可

	LogUtils.setDebugAble(true)
>
###### 如果Fragment页面也需要作为轨迹：

>如果父类是android.app.Fragment，则继承<B>MonitorFragment</B>

>如果父类是android.support.v4.Fragment，则继承<B>MonitorSupportFragment</B>

>在需要的时候重写该方法，用于标识当前fragment是否需要作为轨迹

	  @Override
      public boolean isNeedMonitor() {
            return super.isNeedMonitor();
      }


###### 如果某些View界面也需要作为轨迹：

>在这些View中添加类似如下的代码即可

	class xxView extend View {
        private ViewLifecycleImpl mImpl = new ViewLifecycleImpl();

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            mImpl.onAttached();
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            mImpl.onDetached();
        }

        @Override
        protected void onWindowVisibilityChanged(int visibility) {
            super.onWindowVisibilityChanged(visibility);
            mImpl.onVisibilityChanged(this);
        }
    }


##### 绑定信息
>在AndroidManifest.xml中的application标签下添加(按需添加)

	 <meta-data android:name="domain"
                android:value="应用标识（默认为包名）"/>

>在用户登陆等地方调用<B>MonitorSdk.savePhone(phone)</B>

[![](https://jitpack.io/v/wshychbydh/ActionMonitor.svg)](https://jitpack.io/#wshychbydh/ActionMonitor)
