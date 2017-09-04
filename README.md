# OkNet
一个封装OkHttp请求的库

目前功能还比较简单

主要功能：

  1. 支持post、put、get、delete 请求
  2. 支持取消请求
  3. 支持各种类型的返回值（目前支持String、Bitmap、Gson）
  4. 支持params、jsonParams、path(拼接地址)
 
 后续待加入功能：
  1. 支持rxjava
  2. 支持文件的上传下载
  3. 支持缓存
  4. 支持https
  5. 支持注解配置

当前版本为 ver 1.0


如果有其他的问题可以在github上提出问题或者qq联系我：1360529190， Thanks for you !

---

引入方法：

 - 在你的Project的 build.gradle 按下面的操作配置仓库。
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

 - 然后在你对应的Modlule内的build.gradle内按下面的方式进行引入。

	

```
dependencies {
      compile 'com.github.guohaiyang1992:OkNet:1.0'
	}
```

  
