# 爱你 V1.0版本
学习半年Android后自己做的第一个app，布局、逻辑都是从零设计的。主页面采用ViewPager+fragment实现。分为“照片墙”、“最懂你”、“快学习”、“小助手”四个部分。
## 照片墙
“照片墙”采用瀑布流布局，整体效果表现为图片的悬浮。还有点击查看大图的功能。
![0](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E7%85%A7%E7%89%87%E5%A2%99.png)![1](https://github.com/zhengzhengxiaogege/Android-honey/blob/master/image/%E7%85%A7%E7%89%87%E5%A2%99%E7%82%B9%E5%87%BB%E6%9F%A5%E7%9C%8B.png)


“最懂你”请求聚合数据的星座运势、qq测试、周公解梦接口+缓存实现。“快学习”包括翻译和读书两部分，翻译调用百度翻译接口实现，支持24种语言的相互转换，图书使用LitePal处理聚合数据接口的图书数据实现，点击书名可以查看书的详情。“小助手”使用在阿里云购买的快递查询服务，实现基本的快递查询功能。

        由于是第一个项目，并没有很在意设计模式以及代码规范。
