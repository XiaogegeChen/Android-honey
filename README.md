# 爱你 V1.0版本
学习半年Android后自己做的第一个app，布局、逻辑都是从零设计的。主页面采用**ViewPager+fragment**实现。分为“照片墙”、“最懂你”、“快学习”、“小助手”四个部分。
## 照片墙
“照片墙”采用**瀑布流布局**，整体效果表现为图片的悬浮。还有**点击查看大图**的功能。
![0](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E7%85%A7%E7%89%87%E5%A2%99.png)![1](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E7%85%A7%E7%89%87%E5%A2%99%E7%82%B9%E5%87%BB%E6%9F%A5%E7%9C%8B.png)
## 最懂你
“最懂你”请求聚合数据的星座运势、qq测试、周公解梦接口。由于接口请求次数有限制，所以在对*下拉刷新*和*数据缓存*的处理中有一些细节优化。
### 今日运势
![2](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E6%9C%80%E6%87%82%E4%BD%A0%E4%BB%8A%E6%97%A5.png)
### 本周运势
![3](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E6%9C%80%E6%87%82%E4%BD%A0%E6%9C%AC%E5%91%A8.png)
### 年度运势
![4](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E6%9C%80%E6%87%82%E4%BD%A0%E4%BB%8A%E5%B9%B4.png)
### QQ号测吉凶和周公解梦
![5](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E6%9C%80%E6%87%82%E4%BD%A0qq%E5%92%8C%E8%A7%A3%E6%A2%A6.png)
![6](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/qq%E6%B5%8B%E8%AF%95.png)![7](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E8%A7%A3%E6%A2%A6%E6%B5%8B%E8%AF%95.png)
## 快学习
“快学习”包括翻译和读书两部分，翻译调用百度翻译接口实现，支持24种语言的相互转换。图书使用LitePal处理聚合数据接口的图书数据实现，点击书名可以查看书的详情。
### 翻译
使用下拉框的方式，可以根据需要选择源语种和翻译后的语种<br>
![9](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E5%BF%AB%E5%AD%A6%E4%B9%A0%E7%BF%BB%E8%AF%91%E8%AF%AD%E8%A8%80.png)    ![10](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E7%BF%BB%E8%AF%91%E6%B5%8B%E8%AF%95.png)
### 图书
使用开源项目```litepal```操作数据库,数据来源于聚合数据。初始化时开启异步任务请求聚合数据提供的所有类别的图书数据并写入数据库。点击**换一批**可以换一批图书查看。这个版本没有加更新数据库的方法
![11](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E5%BF%AB%E5%AD%A6%E4%B9%A0%E5%9B%BE%E4%B9%A6.png)<br>
点击书名查看图书详情<br>
![12](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E4%B9%A6%E8%AF%A6%E6%83%85.png)![13](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E4%B9%A6%E8%AF%A6%E6%83%851.png)
## 小助手
“小助手”使用在阿里云购买的快递查询服务，实现基本的快递查询功能。<br>
![14](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E5%B0%8F%E5%8A%A9%E6%89%8B.png)
### 查询结果
![15](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E5%BF%AB%E9%80%92%E7%BB%93%E6%9E%9C.png)![16](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E5%BF%AB%E9%80%92%E4%BF%A1%E6%81%AF1.png)![17](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E5%BF%AB%E9%80%92%E4%BF%A1%E6%81%AF2.png)
## 其他
包括**右划查看app信息**以及**双击退出**等等<br>
![18](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E5%8F%B3%E5%88%92.png)<br><br>
***由于是第一个项目，并没有特别注意设计模式以及代码规范,会在以后的项目中改进。***
