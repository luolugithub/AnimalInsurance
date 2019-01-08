bugly
地址：https://bugly.qq.com/v2/workbench/apps
账号：1322976490（翔创科技QQ）
密码：asd123!@#


接口流程
投保
2.1 新增保单/baodan/addApp
--》2.7 zip 文件上传接口 /pigApp/upload 
--》2.5 新增牲畜信息/pigInfo/addApp 
--》2.22 投保建库、理赔验证、已理赔查询接口 /pigApp/cow （type=1）
--> 2.23 投保建库、理赔验证、查询结果查询接口/pigApp/queryResult(pid) 
理赔
2.6	新增理赔信 /pigLipei/addApp	
--> 2.7 zip 文件上传接口  /pigApp/upload
--> 2.22 投保建库、理赔验证、已理赔查询接口 /pigApp/cow （type=2）
--> 2.23 投保建库、理赔验证、查询结果查询接口/pigApp/queryResult(pid) 
--> 2.5 新增牲畜的信息 /pigInfo/addApp
--> 2.22 投保建库、理赔验证、已理赔查询接口 /pigApp/cow （type=3）
--> 2.23 投保建库、理赔验证、查询结果查询接口/pigApp/queryResult(pid) 



20180807
畜险APP版本1.12
下载地址：
https://www.pgyer.com/NpzH
更新内容：
1，图像采集模块，新增满足采集数量自动跳转的功能；
2，采集数据提交对话框添加采集人，修改日期的格式；
3，图片分类的角度限制还原为733格式；
4，在上一个版本的基础上修复理赔流程理赔验证失败服务器接口返回为空的异常bug；

20180808
畜险APP版本1.13
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，理赔流程分析，修复理赔验证时重复调用的服务端且两次调用的pid不同的bug；
2，修改理赔流程投保库查询返回为空时给用户的错误提示信息；
3，图像采集模块，满足采集数量弹出mSendView对话框闪烁的bug修复；
4，添加投保流程资料采集界面地址信息获取异常的异常处理；

20180809
畜险APP版本1.14
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，修改角度提示信息；

20180809
畜险APP版本1.16
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，新增离线投保功能；

20180810
畜险APP版本1.19
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，费率百分比的布局显示；
2，新增保单界面营业执照上传失败的错误提示修改；
3，注册界面，所属公司选择栏禁用键盘输入；
4，分公司选择支持选择到县；

20180811
畜险APP版本1.21
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，修复新增保单营业执照图片上传异常的bug；

20180811
畜险APP版本1.22
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，修复离线投保数据上传的bug；

20180813
畜险APP版本1.23
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，修复离线投保数据上传成功和失败相反的bug；

20180814
畜险APP版本1.28
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，优化离线上传的进度显示；
2，优化图像采集界面的提示；
3，图片采集数量设置上限；
4，添加“散养户”字段；

20180814
畜险APP版本1.29
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，优化上传失败文件的判断阈值；

20180818
牛险APP版本1.35
下载地址：https://www.pgyer.com/6TP5
更新内容：
1，优化网络请求接口；

20180820
驴险APP版本1.38
下载地址：https://www.pgyer.com/6TP5
更新内容：
1，更新驴脸分类模型；
2，录制视频质量改为标清；

20180820
驴险APP版本1.39
下载地址：https://www.pgyer.com/6TP5
更新内容：
1，去掉视频上传；

20180820
驴险APP版本1.41
下载地址：https://www.pgyer.com/6TP5
更新内容：
1，更新驴脸本地分类模型；
2，更新角度范围限制；

20180820
驴险APP版本1.42
下载地址：https://www.pgyer.com/6TP5
更新内容：
1，资料采集界面点击提交闪回首页的bug修复；

20180820
驴险APP版本1.43
下载地址：https://www.pgyer.com/6TP5
更新内容：
1，本地角度分类模型改为牛脸模型；

20180820
驴险APP版本1.44
下载地址：https://www.pgyer.com/6TP5
更新内容：
1，本地分类模型为驴脸分类模型，试用！

20180821
驴险APP版本1.45
下载地址：https://www.pgyer.com/6TP5
更新内容：
1，去掉当投保库和理赔库中都验证成功的时候的相识度的判断；
2，本地分类模型为驴脸分类模型，试用！

20180821
牛险APP版本1.50
下载地址：https://www.pgyer.com/NpzH
更新内容：
1，去掉当投保库和理赔库中都验证成功的时候的相似度的判断；
2，理赔流程提交图片时闪回bug修复；
3，资料采集界面提交按钮事件网络请求占用UI线程导致闪退的bug修复；
4，新增保单界面的银行卡、身份证和营业执照图片压缩；
5，资料采集界面的提交按钮添加进程提示的dialog；

20180823
驴险APP版本1.52
下载地址：https://www.pgyer.com/6TP5
更新内容：
1, 添加视频压缩上传；
2，修改截图尺寸为360*480；
3，新增图片包和视频包是上传失败后的重传逻辑；

20180829
农险APP版本1.68
下载地址：https://www.pgyer.com/yaRV
更新内容：
1，资料采集界面的提交按钮的防御逻辑添加；
2，图像采集界面的实时提示信息矫正；
3，理赔库查询状态异常的捕获逻辑修正；

20180830
农险APP版本1.79（245服务器）
下载地址：https://www.pgyer.com/yaRV
更新内容：
1，新增保单界面的验标时间添加默认当天的日期处理；
2，新增保单服务器返回异常时的捕获和逻辑处理；
3，网络连接异常状态投保建库失败的跳转界面修改为跳回采集界面；
4，新开始按钮的点击事件当前图片文件夹计数器未被情况的bug修复；
5，图像采集模快的流程优化；


20180830
农险APP版本1.80（61服务器）
下载地址：https://www.pgyer.com/yaRV
更新内容：
1，修复理赔时传值为空的bug；

20180901
农险APP版本1.89（61测试服务器）
下载地址：https://www.pgyer.com/yaRV
更新内容：
1，图像采集竖屏版本；
2，驴模型送给CNN图像形状改为正方形；
3，角度提示框的图片变形修正；

20180903
农险APP版本1.91（61测试服务器）
下载地址：https://www.pgyer.com/yaRV
更新内容：
1，图像采集竖屏版本；
2，驴模型“0902.pb”；
3，录制按钮文案修改；
4，登陆模块的状态吐司提示；
5，三个分类器的跟踪框的位置矫正；

20180912
农险APP测试版本1.99（61测试服务器）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，更新猪、牛和驴的角度分类限制；
2，牛的分类器输入图片按短边裁剪为正方形；
3，图像采集提示框中的新开始按钮事件的处理逻辑优化；

20180912
农险APP测试版本2.00（61测试服务器）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，将模型的box数据和score数据与角度数据一起打印到图片文件夹中的TXT文件中；

20180912
农险APP测试版本2.01（61测试服务器）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，当理赔失败事件被触发时，重试按钮处理逻辑的优化；

20180913
农险APP测试版本2.03（61测试服务器）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，弃用“number.txt”文件；
2，截图框去掉padding，保留模型的输出框进行缩放；

20180913
农险APP测试版本2.04（61测试服务器）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，数据采集保存录制视频并上传；

20180917
跟踪画框优化
农险APP测试版本2.09（61测试服务器）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，相机预览尺寸640*480；
2，添加画框角度提示；
3，更新猪脸角度分类模型；
4，资料采集界面，继续录入按钮的错误提示修正；
5，画框状态提示信息修改；

20180919
跟踪画框优化
农险APP测试版本2.12（61测试服务器）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，猪牛驴模型更新；
2，猪的角度分类更新，截图框扩大1.125倍；

20180922
理赔优化分支 + 跟踪画框优化分支
农险APP测试版本2.19（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，把优化的理赔流程分支更新优化的跟踪画框分支；
2，扩大jvm内存；

20180925
理赔优化分支 + 跟踪画框优化分支
农险APP测试版本2.21（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，驴的模型更新为检测+角度预测tflite版本的模型；
2，驴的角度信息提示修正；

20180926
理赔优化分支 + 跟踪画框优化分支
农险APP测试版本2.22（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，驴的模型更新为检测+角度预测tflite版本的模型；
2，猪的角度分类限制调整和阈值调整；
3，studio版本升级；

20180930
farmInsurePotraitDeveloperBranch
********* 算法测试专用 **********
农险APP测试版本2.24（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，驴的padding图片的保存和在padding图片上的画框图保存；

20180930
farmInsurePotraitDeveloperBranch

农险APP测试版本2.26（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，注册新增邀请码的功能；
2，新增企业和组织的保单、验标单管理；
3，修复保单iterm的详情点击崩溃bug；

20181007
farmInsurePotraitDeveloperBranch

农险APP测试版本2.28（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，驴、牛和猪的分类器的截图优化；
2，接口返回后的打印日志位置调整；

20181009
farmInsurePotraitDeveloperBranch

农险APP测试版本2.30（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，投保流程图像上传接口和新增牲畜接口优化；
2，接口添加经纬度字段；

20181009
farmInsurePotraitDeveloperBranch

农险APP测试版本2.33（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，牛和猪的bitmap在送入模型前的预处理优化；
2，牛和猪的截图以及缩放方法优化；

20181010
farmInsurePotraitDeveloperBranch

农险APP测试版本2.36（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，关键词搜索时改为button点击事件；
2，关键字查询验标单接口新增用户ID字段；
3，农险选择对话框移到登陆界面；

20181010
farmInsurePotraitDeveloperBranch

农险APP测试版本2.38（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，创建验标单接口添加“uid”字段；
2，保单条目验标号显示改为在显示验标名称不为空时显示；

20181011
farmInsurePotraitDeveloperBranch

农险APP测试版本2.43（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，添加新建企业保单和组织保单的各个参数的校验逻辑；
2，注册的邀请码逻辑造成的错误提示的bug修正；

20181011
farmInsurePotraitDeveloperBranch

农险APP测试版本2.44（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，登陆和注册模块的提示信息优化；
2，添加新建企业保单和组织保单uid字段的完善；

20181011

农险APP生产版本1.91（245生产服务器,8081端口）
下载地址：http://www.pgyer.com/pgyer.com/yaRV
更新内容：
1，登陆和注册模块的提示信息优化；
2，添加新建企业保单和组织保单的业务；
3，投保流程优化；
4，分类器的截图优化；

20181012
农险APP生产版本1.92（245生产服务器,8081端口）
下载地址：http://www.pgyer.com/pgyer.com/yaRV
更新内容：
1，按钮样式统一；
2，新建验标单按钮事件中去掉GPS未知权限的判断；

20181015
farmInsurePotraitDeveloperBranch

农险APP测试版本2.46（61测试服务器,8086端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，驴的模型截图添加关键点判断的逻辑；
2，牛的模型更新为检测+角度预测+关键点预测的TensorFlow lite模型，并添加关键点的判断；

20181016
farmInsurePotraitDeveloperBranch

农险APP测试版本2.47（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，牛的角度预测模型更新；
2，牛的角度分类修改；

20181016
farmInsurePotraitDeveloperBranch

农险APP测试版本2.48（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，原图分辨率改为1280*960；

20181017
farmInsurePotraitDeveloperBranch

农险APP测试版本2.49（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，牛的截图扩边至原大小的1.2倍；

20181017
farmInsurePotraitDeveloperBranch

农险APP测试版本2.53（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，离线验标单已知bug修复，数据展示逻辑优化；
2，新增保单验标单的界面优化调整；
3，猪牛驴的模型后处理添加裁剪图的动态缩放逻辑；

20181017
farmInsurePotraitDeveloperBranch

农险APP测试版本2.54（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，离线的json文件的参数bug修复；
2，隐藏理赔界面的保单搜索；

20181018
farmInsurePotraitDeveloperBranch

农险APP测试版本2.57（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，猪险本地分类模型更新为猪脸检测、角度预测和关键点判断模型；
2，离线投保流程最后的完成按钮跳转到首页的断网模式下的网络请求异常处理以及农险所有的网络请求接口断网模型下的请求异常处理；
3，农险测试版APP集成极光推送SDK；
4，本地分类器保存padding原图（路径：/sdcard/innovation）；

20181018
farmInsurePotraitDeveloperBranch

农险APP测试版本2.58（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，牛的角度修改和关键点判断修改；

20181018
farmInsurePotraitDeveloperBranch

农险APP测试版本2.61（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，猪牛驴本地分类器保存原图、画框图和关键点画图（路径：/sdcard/innovation）；
20181018
farmInsurePotraitDeveloperBranch

农险APP测试版本2.63（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，费率的输入控制位数为3为同时添加是否合法输入的逻辑校验；
2，牲畜险种类选择对话框移到首页进行选择；
3，数据采集模块添加角度提示和图片保存数量同步的逻辑；

农险APP测试版本2.64（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，修复1.63已知bug；

农险APP测试版本2.66（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，驴的检测模型更新和检测阈值调整；
2，投保只上传图片，理赔上传视频和图片；

农险APP测试版本2.67（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，保存猪牛驴的画框图、裁剪图和关键点图了（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）；

20181020
农险APP生产版本1.93（245生产服务器,8081端口）
下载地址：http://www.pgyer.com/pgyer.com/yaRV
更新内容：
1，猪牛驴的本地分类模型为tflite；
2，理赔上传视频；

20181022
farmInsurePotraitDeveloperBranch
农险APP测试版本2.68（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，更新牛的角度限制和猪牛驴的角度数量判断限制；
2，保存猪牛驴的画框图、裁剪图和关键点图了（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）；

20181023
farmInsurePotraitDeveloperBranch
农险APP测试版本2.70（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，更新猪的位置检测模型、角度预测模型和关键点模型；
2，保存关键点的模型输出；
3，驴的检测模型调整；
4，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）；


20181023
算法测试
农险APP测试版本2.73（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，更新牛的检测模型和关键点模型，猪的关键点模型和判断条件；
2，在截图的基础上保存原图；

20181025
farmInsurePotraitDeveloperBranch
农险APP测试版本2.75（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，更新猪牛驴的截图算法；
2, 理赔图片上传接口和信息比对接口优化；
3，在截图的基础上保存原图；（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Current/图片/）
4，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）

20181025
farmInsurePotraitDeveloperBranch
农险APP测试版本2.78（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，牛的角度限制修改，猪的关键点限制修改；
2, 投保和理赔都上传视频；
3，猪牛驴的截图后保存前做指定比例的padding然后压缩为指定大小；
4，在截图的基础上保存原图；（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Current/图片/）
5，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）

20181026
farmInsurePotraitDeveloperBranch
农险APP测试版本2.79（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，更新猪的关键点模型和关键点判断条件；
2，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）

20181026
farmInsurePotraitDeveloperBranch
农险APP测试版本2.80（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，更新猪的检测模型和关键点模型；
2，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）

20181026
farmInsurePotraitDeveloperBranch
农险APP测试版本2.82（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，添加查看是否录制视频接口的逻辑；
2，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）

20181027
farmInsurePotraitDeveloperBranch
农险APP测试版本2.84（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，添加图像质量的检查逻辑；
2，将模型输出的角度显示到屏幕上；
2，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）

20181031
farmInsurePotraitDeveloperBranch
农险APP测试版本2.87（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，本地分类器输入的原图更新；
2，跟踪画框的显示偏移矫正；
3，模型输出的角度显示数据更新；
4，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）


20181101
farmInsurePotraitDeveloperBranch

更新内容：
1，采集视频分类保存到本地（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/“TestVideoFailed / TestVideoSuccess”)；
2，保存进入分类器的原图、检测模型的原图、检测后的裁剪图，保存关键点模型判断成功后的识别图画点并标记点的具体信息、关键点模型判断失败后的图，
    以及角度模型判断成功和失败的原图并标记模型输出的结果；（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/)
3，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）

20181102
farmInsurePotraitDeveloperBranch
农险APP测试版本2.91（61测试服务器,8081端口）
下载地址：http://www.pgyer.com/uBFc
更新内容：
1，在2.89的基础上添加对进入分类器的单张图片的跟踪；

20181107
farmInsurePotraitDeveloperBranch

更新内容：
1，关键点判断条件优化，角度预测模型和关键点判断联合判断优化；
2，本地分类器检测到多框时，提示用户检测到干扰对象，并作出调整；
3，采集视频分类保存到本地（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/“TestVideoFailed / TestVideoSuccess”)；
4，保存进入分类器的原图、检测模型的原图、检测后的裁剪图，保存关键点模型判断成功后的识别图画点并标记点的具体信息、关键点模型判断失败后的图，
    以及角度模型判断成功和失败的原图并标记模型输出的结果；（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/)
5，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）

20181110
modelDeveloperBranch

更新内容：
1，视频分辨率1280*960；
2，单头压缩包保存路径（/sdcard/Android/data/com.innovation.animal_model_test/cache/innovation/animal）；
3，牛的侧脸角度改为25到90；
4，采集视频分类保存到本地（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/“TestVideoFailed / TestVideoSuccess”)；
5，保存进入分类器的原图、检测模型的原图、检测后的裁剪图，保存关键点模型判断成功后的识别图画点并标记点的具体信息、关键点模型判断失败后的图，
    以及角度模型判断成功和失败的原图并标记模型输出的结果；（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/)
6，保存猪牛驴的原图、裁剪图和关键点画图（路径：/sdcard/Android/data/com.innovation.nongxian_test/cache/innovation/animal/投保/Test/）