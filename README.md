## AndroidLog</br>
用于记录 Android 运行时产生的日志、Crash 等信息并保存到本地文件中的框架。</br>
## 使用方式</br>
首先添加 AndroidLog 依赖：</br>
```
compile 'com.github.0xZhangKe:ZLog:3.0'
```
在使用前需要通过日志的输出文件夹对其初始化，例如下面这样：</br>
```
ZLog.Init(String.format("%s/log/", getExternalFilesDir(null).getPath()));
```
一般来说可以在 Application 中的 onCreate() 方法中对其初始化，初始化完成后即可直接使用其中提供的方法输出日志，其中提供了一系列的打印日志的方法，使用方式如下：</br>
```
ZLog.e("TAG", "Internet Error");
```
其中的日志输出的方法参数等等都是按照 Android 的日志工具 Log 来的，并且在调用 AndroidLog 的方法后会自动调用 Log 对应的方法输出日志；</br>
所以完全可以使用 AndroidLog 替代 Log，因为使用 AndroidLog 打印日志时，不仅会输出到文件中，还会调用 Log 中的相关方法打印到控制台；</br>
为了灵活控制日志是否输出到日志文件还是单纯的只输出到控制台中，其中提供了两种控制方式控制其行为（默认情况下，日志会同时输出到控制台及日志文件中）。</br>
第一种：调用 openSaveToFile() 或 closeSaveToFile() 方法开启或关闭整个 ZLog 框架的行为，比如只要调用了 closeSaveToFile() 方法，那么直到你调用 openSaveToFile() 方法前，所有的日志都不会输出到文件中，反之亦然；</br>
第二种：针对单条日志文件，可以传一个 boolean 类型的参数控制，比如 AndroidLog.e("TAG", "Internet Error"， false) ,那么此条日志记录就不会输出到文件中。</br>
具体可以查看下面的类文档。</br>
## 日志文件的保存规则：</br>
Error 类型日志保存到对应目录下的 errorLog1.txt 中，如果 errorLog1.txt 大小超过 1MB，则重新输出到 errorLog2.txt 中，以此类推，当 Error 类型的文件个数超过 9 个时，会自动清理所有 Error 类型的日志文件，并从 errorLog1.txt 重新开始；</br>
INFO、DEBUG、WTF 类型的日志会保存到 log.txt 中，其他规则与同上；</br>
CRASH 类型的文件会保存到 crash.txt 中，其他规则同上。</br>
## 实现原理
在调用 ZLog.Init(String) 方法之后会通过日志保存的路径创建并初始化一个 LogQueue 对象并调用其 start() 方法，往后每次调用输出日志的方法都会像其中添加一条记录；</br>
LogQueue 在初始化时会创建一个 LinkedBlockingQueue 阻塞队列用于存储日志，同时会通过日志保存的路径及LinkedBlockingQueue创建一条 LogDispatcher 线程，在调用其 start() 方法时，会启动 LogDispatcher 线程；</br>
LogDispatcher 内有一个死循环用于不停地调用 take() 方法从阻塞队列 LinkedBlockingQueue 中取出一条日志信息并打印，如果 LinkedBlockingQueue 内没有日志数据线程则进入阻塞状态，直到有新的日志。</br>

## 类文档</br>
### ZLog</br>

##### public static synchronized void Init(String logDir)<br>
初始化 ZLog， 必须先调用 Init 方法<br>
**Parameters:**<br>
logDir -> 保存日志的文件夹</br>
</br>
##### public static void e(String TAG, String text)<br>
输出错误信息，该方法内部会调用 Log.e(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
</br>
##### public static void e(String TAG, String text, boolean saveToFile)<br>
输出错误信息，该方法内部会调用 Log.e(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
saveToFile -> 是否将该条日志输出到文件中<br>
</br>
##### public static void e(String TAG, String text, Throwable e)<br>
输出错误信息，该方法内部会调用 Log.e(TAG, text, e) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
e -> 异常信息<br>
</br>
##### public static void e(String TAG, String text, Throwable e, boolean saveToFile)<br>
输出错误信息，该方法内部会调用 Log.e(TAG, text, e) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
e -> 异常信息<br>
saveToFile -> 是否将该条日志输出到文件中<br>
</br>
##### public static void d(String TAG, String text)<br>
输出 debug 信息，该方法内部会调用 Log.d(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
</br>
##### public static void d(String TAG, String text, boolean saveToFile)<br>
输出 debug 信息，该方法内部会调用 Log.d(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
saveToFile -> 是否将该条日志输出到文件中<br>
</br>
##### public static void i(String TAG, String text)<br>
输出 info 信息，该方法内部会调用 Log.i(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
</br>
##### public static void i(String TAG, String text, boolean saveToFile)<br>
输出 info 信息，该方法内部会调用 Log.i(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
saveToFile -> 是否将该条日志输出到文件中<br>
</br>
##### public static void i(String TAG, String text, Throwable e)<br>
输出 info 信息，此方法会将 text 与 e 组合之后输出，该方法内部会调用 Log.i(TAG, String.format("%s--->%s", text, e.toString())) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
e -> 异常信息<br>
</br>
##### public static void i(String TAG, String text, Throwable e, boolean saveToFile)<br>
输出 info 信息，此方法会将 text 与 e 组合之后输出，该方法内部会调用 Log.i(TAG, String.format("%s--->%s", text, e.toString())) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
e -> 异常信息<br>
saveToFile -> 是否将该条日志输出到文件中<br>
</br>
##### public static void wtf(String TAG, String text)<br>
输出 wtf 信息，该方法内部会调用 Log.wtf(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
</br>
##### public static void wtf(String TAG, String text, boolean saveToFile)<br>
输出 wtf 信息，该方法内部会调用 Log.wtf(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
saveToFile -> 是否将该条日志输出到文件中<br>
</br>
##### public static void crash(String TAG, String text, boolean saveToFile)<br>
输出 crash 信息，该方法内部会调用 Log.e(TAG, text) 方法将日志输出到控制台<br>
**Parameters:**<br>
TAG -> TAG 标签<br>
text -> 要输出的文本<br>
</br>
#### public static synchronized void openSaveToFile()<br>
打开将日志保存到文件的开关
#### public static synchronized void closeSaveToFile()<br>
关闭将日志保存到文件的开关
