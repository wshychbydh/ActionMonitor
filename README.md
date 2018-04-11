# ActionMonitor
### 用户行为数据采集（按行为轨迹采集）

### 用户行为规则
1、A -> B -> A<br>
2、A -> B -> Home<br>
3、B -> C -> A ->...-> B或者Home<br>

### 添加依赖
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

### 使用说明

在Application的onCreate中添加

	MonitorSdk.init(this);

>如果需要打印日志，打开日志开关即可

	MonitorSdk.init(this, true);

##### 默认会将所有Activity作为轨迹
   如果希望某个Activity被过滤掉，则可在该class上面添加@Ignore注解:
   
    @Ignore
    class xxActivity extends Activity{
        ```
    }
注意：如果Activity被过滤，则该Activity上所有加载的页面都会被过滤(强制)

##### 如果Fragment页面也需要作为轨迹：

>如果父类是android.app.Fragment，则继承<B>MonitorFragment</B>

>如果父类是android.support.v4.Fragment，则继承<B>MonitorSupportFragment</B>

>在需要的时候重写该方法，用于标识当前fragment是否需要作为轨迹，默认由Activity是否作为轨迹来判断
 
      //通常情况下，无需重写该方法
	  @Override
      public boolean isNeedMonitor() {
          return super.isNeedMonitor();
      }


##### 如果某些View界面也需要作为轨迹：

>在这些View中添加类似如下的代码即可

	class xxView extend View {
        private ViewLifecycleImpl mImpl = new ViewLifecycleImpl(getContext());
        
        public xxView(Context context) {
           super(context);
           //默认由Activity是否作为轨迹来判断，无需调用该方法
           mImpl.setNeedMonitor(true); 
        }
        
        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            mImpl.onAttached(this);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            mImpl.onDetached();
        }

        @Override
        protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
            super.onVisibilityChanged(changedView, visibility);
            mImpl.onVisibilityChanged(visibility);
        }
    }


#### 绑定信息
>在AndroidManifest.xml中的application标签下添加(按需添加)

	 <meta-data android:name="domain"
                android:value="应用标识（默认为包名）"/>
                
     <meta-data android:name="channel"
                android:value="应用渠道"/>

>在用户登陆及判断登陆(已登录用户)状态的地方分别调用

     MonitorSdk.savePhone(phone)


#### SDK中的权限如下：（有则获取，不会主动请求）

       <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
       <uses-permission android:name="android.permission.GET_TASKS" />
       <uses-permission android:name="android.permission.INTERNET" />
       <uses-permission android:name="android.permission.READ_PHONE_STATE" />
       <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
       <!-- 仅网络定位的权限 -->
       <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

#### Demo地址：https://github.com/wshychbydh/ActionDemo



[![](https://jitpack.io/v/wshychbydh/ActionMonitor.svg)](https://jitpack.io/#wshychbydh/ActionMonitor)
