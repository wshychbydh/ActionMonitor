# ActionMonitor
#### 用户行为数据采集（按行为轨迹采集）
该版本只有按用户行为轨迹采集数据，用户点击等行为暂时不采集。

#### 用户行为规则
1、A -> B -> A

2、A -> B -> Home

3、B -> C -> A ->...-> B或者Home

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
###### 如果某些Fragment的页面和View界面也需要作为轨迹：

>如果父类是android.app.Fragment，则继承<B>MonitorFragment</B>

>如果父类是android.support.v4.Fragment，则继承<B>MonitorSupportFragment</B>


##### 绑定信息

>在用户登陆等地方调用<B>MonitorSdk.saveUser(userInfo)</B>

[![](https://jitpack.io/v/wshychbydh/ActionMonitor.svg)](https://jitpack.io/#wshychbydh/ActionMonitor)
