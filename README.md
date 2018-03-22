# ActionMonitor
#### 用户行为数据采集（按行为轨迹采集）
通过代码注入的方式实现监听按钮点击、长按、选中状态
#### 用户行为规则
1、A -> B -> A

2、A -> B -> Home

3、B -> C -> A ->...-> B或者Home

#### 添加代码
在Application里面添加如下代码：
registerActivityLifecycleCallbacks(new ActivityLifecycleImpl());

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

[![](https://jitpack.io/v/wshychbydh/ActionMonitor.svg)](https://jitpack.io/#wshychbydh/ActionMonitor)
