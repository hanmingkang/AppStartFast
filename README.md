# AppStartFast

#### 依赖指南
Gradle：
```java
    allprojects {
      repositories {
         ...
         maven { url 'https://jitpack.io' }
      }
   }

   dependencies {
               implementation 'com.github.hanmingkang:AppStartFast:0.0.2'
       }
```

#### 原理解析
利用有向无环图标记进入线程池的顺序，避免死锁，通过countDownLatch规定任务的执行顺序。后续可优化细节

优化：
1. 线程池多种，例如：cpu型，io型
2. 健壮性，例如只能在主线程执行等
3. 套入AppStartUp方案，使用ContentProvider实现启动，开发者无需手动启动
