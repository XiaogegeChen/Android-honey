package com.github.xiaogegechen.bing.model.json;

import com.github.xiaogegechen.common.network.MyServerCheckable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageJSON implements MyServerCheckable {

    /**
     * result : {"module_type":200,"topic_type":1006,"pic_count":35,"pic_list":[{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.f7JJKSX2Vf5a0bJKSu1HnAHaEh&pid=15.1","pic_real_url":"http://www.mingxingku.com/Data/Images/Articles/2016-09-20/3199696607.jpg","pic_from_url":"http://www.mingxingku.com/photo-106434"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.ZfLuu0KFbHD1wv8IzuMxtwHaIG&pid=15.1","pic_real_url":"http://img.xiumu.cn/2016/0920/20160920093707476.jpg","pic_from_url":"http://www.xiumu.cn/yule/a/201609/177258.html"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.c1gbV_9jlmXCOiuan8AsbwHaJs&pid=15.1","pic_real_url":"http://www.mingxingku.com/Data/Images/Articles/2016-09-20/4284578295.jpg","pic_from_url":"http://www.mingxingku.com/photo-106434"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.Ii4AQY0Fay6evEEM9qfO5AHaE6&pid=15.1","pic_real_url":"http://img.mp.itc.cn/upload/20160921/0fe120084ab74881a916a81e1bf83ee5_th.jpg","pic_from_url":"http://www.sohu.com/a/114749600_425340"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.mKSz-B73GYbJBN-U_3yFQQHaE8&pid=15.1","pic_real_url":"http://m1.biz.itc.cn/pic/new/n/24/74/Img8717424_n.jpg","pic_from_url":"http://pic.yule.sohu.com/detail-755989-2.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.E0M8NQHed082fc3bva5wlQHaE6&pid=15.1","pic_real_url":"http://pic.vdfly.com/2016/0920/20160920025632164.jpg","pic_from_url":"http://news.vdfly.com/dujia/201609/561241.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.PiU9eyFK3aAzX-zAmovWAQAAAA&pid=15.1","pic_real_url":"http://images.china.cn/attachement/jpg/site1000/20161012/c03fd55e3b6d1967f1f54d.jpg","pic_from_url":"http://www.china.com.cn/ent/2016-10/12/content_39471329.htm"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.217RKKvGUFV9GhmL2dVnewHaJz&pid=15.1","pic_real_url":"http://www.mingxingku.com/Data/Images/Articles/2016-09-20/2718704659.jpg","pic_from_url":"http://www.mingxingku.com/star-744-picture"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.Ua6mMXSEl1LrMkFhv5CSdAHaKQ&pid=15.1","pic_real_url":"http://img4.cutv.com/images/2016/9/20/20169201474337874668_319.jpg","pic_from_url":"http://news.cutv.com/tupian/2016-9-20/1474338780167.shtml"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.sDnFYQJhlPz9X4GivNp8DgHaG9&pid=15.1","pic_real_url":"http://www.mingxingku.com/Data/Images/Articles/2016-09-21/2179090064.jpg","pic_from_url":"http://www.mingxingku.com/photo-106611"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.grFg1ZQ_sHW8eVAdBjJRewHaEK&pid=15.1","pic_real_url":"http://img1.3lian.com/2015/w22/22/d/101.jpg","pic_from_url":"http://www.xiaoshou.info/%E8%83%A1%E6%AD%8C%E5%86%99%E7%9C%9F/"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.ejLRqrrLXkk1LANVC1ulSAHaI1&pid=15.1","pic_real_url":"http://m4.biz.itc.cn/pic/new/n/43/62/Img8226243_n.jpg","pic_from_url":"http://www.xici.net/d226900575.htm"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.pHZnHMAtIiB6Hvv4Dlv2owAAAA&pid=15.1","pic_real_url":"http://m1.biz.itc.cn/pic/new/n/60/62/Img8226260_n.gif","pic_from_url":"http://www.xici.net/d226900575.htm"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP._UtI73PBNO2ChFPZiHyCtQHaEK&pid=15.1","pic_real_url":"http://img1.3lian.com/2015/w22/22/d/103.jpg","pic_from_url":"http://www.xiaoshou.info/%E8%83%A1%E6%AD%8C%E5%86%99%E7%9C%9F/"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.Jwk1NJ9IFN5I3X6ILN2RywHaI1&pid=15.1","pic_real_url":"http://m3.biz.itc.cn/pic/new/n/42/62/Img8226242_n.jpg","pic_from_url":"http://www.xici.net/d226900575.htm"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.n_c3jF7XH1i7ZygrQcrTwwAAAA&pid=15.1","pic_real_url":"http://www.86kx.com/uploads/allimg/160107/2295_160107163424_2.jpg","pic_from_url":"http://www.86kx.com/html/2016/0107/84645.html"},{"pic_thumbnail_url":"https://tse3-mm.cn.bing.net/th?id=OIP.P00RvEThRbLAWkas1qAuUAAAAA&pid=15.1","pic_real_url":"http://m2.biz.itc.cn/pic/new/n/61/62/Img8226261_n.gif","pic_from_url":"http://pic.yule.sohu.com/detail-706964-6.shtml"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.DrBnu_HYjwiuSuo6FmNjNQHaK3&pid=15.1","pic_real_url":"http://himg2.huanqiu.com/attachment2010/2016/0921/20160921121416604.jpg","pic_from_url":"http://fashion.huanqiu.com/news/2016-09/9468424.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.5SRoFyvm0EIDR02Zn_J-YQHaI1&pid=15.1","pic_real_url":"http://m1.biz.itc.cn/pic/new/n/44/62/Img8226244_n.jpg","pic_from_url":"http://pic.yule.sohu.com/detail-706964-3.shtml"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.LsmPsOXDkfotb-LPafisQQAAAA&pid=15.1","pic_real_url":"http://y2.ifengimg.com/ifengimcp/pic/20160107/e702e0917230257093a0_size1296_w396_h220.gif","pic_from_url":"http://www.52rkl.cn/funlaile/010G1213R016.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.XHCwRNJ-4u19JG-FKR47EwEgDY&pid=15.1","pic_real_url":"https://img3.yxlady.com/yl/UploadFiles_5361/20160921/20160921180224431.jpg","pic_from_url":"http://ent.yxlady.com/201609/1300684.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.OPdKY2o9aYz30jcMRbtTvAHaEO&pid=15.1","pic_real_url":"http://img2.jiemian.com/101/original/20151206/144936887615266100.jpg","pic_from_url":"http://www.jiemian.com/article/463598.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.5Rc3dV7uYoIj30oe-qlfjgHaE7&pid=15.1","pic_real_url":"http://www.hi.chinanews.com/hnnew/2016-09-20/U63P16T1D424019F8DT20160920162304.jpg","pic_from_url":"http://www.hi.chinanews.com/hnnew/2016-09-20/424019.html"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.3ESxLliZRybjeXtBKVRp4QHaJt&pid=15.1","pic_real_url":"http://img.mingxing.com/mingxing20180111/0f617ddf9a1baced3f4b7224d40c0be6.jpg","pic_from_url":"http://www.mingxing.com/news/index/id/323364.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.X6XMfrGXLvBL-dXl_TgRwwAAAA&pid=15.1","pic_real_url":"http://n.sinaimg.cn/ent/transform/500/w300h200/20190107/RqwM-hrfcctn5099981.jpg","pic_from_url":"http://ent.sina.com.cn/s/m/2019-01-06/doc-ihqhqcis3627444.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.okh-6EX29z4P9HfnxErIawAAAA&pid=15.1","pic_real_url":"https://img.4hw.com.cn/20190107/fe4ffe5a97d75ea889a4260df083a0ee.jpg!titlepic","pic_from_url":"https://yule.4hw.com.cn/news/201901/616_238837.html"},{"pic_thumbnail_url":"https://tse3-mm.cn.bing.net/th?id=OIP.7F7ITdtpbKxNpDZgAX5IdAHaE7&pid=15.1","pic_real_url":"http://upload.taihainet.com/2016/0921/1474416743606.jpg","pic_from_url":"http://www.taihainet.com/news/pastime/yllq/2016-09-21/1790921.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.tsLrn9yQ1M9vD9plpY0mMwAAAA&pid=15.1","pic_real_url":"http://y2.ifengimg.com/ifengimcp/pic/20160107/79c80989d2abf094c312_size816_w396_h220.gif","pic_from_url":"http://www.52rkl.cn/funlaile/010G1213R016.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.7utHnJ92d3pq6KPNyFVnQwAAAA&pid=15.1","pic_real_url":"https://2img.hitv.com/preview/cms_icon/2019/1/7/01/20190107110425777.jpg_366x206.jpg","pic_from_url":"https://www.mgtv.com/"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.J7AEycMqyMRBlSh_R4lGdAHaE8&pid=15.1","pic_real_url":"http://www.sd.chinanews.com/2/2016/0920/U415P935T2D24972F12DT20160920143415.jpg","pic_from_url":"http://www.sd.chinanews.com/2/2016/0920/24972.html"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.87DZd3SiVDzBHSkEglkN9wDMEy&pid=15.1","pic_real_url":"http://m4.biz.itc.cn/pic/new/n/07/74/Img8717407_n.jpg","pic_from_url":"http://pic.yule.sohu.com/detail-755989-25.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.rK2OtJCUwcHrMIK71MpIlAHaNJ&pid=15.1","pic_real_url":"http://d.5857.com/gg_160616/desk_001.jpg","pic_from_url":"http://www.xiaoshou.info/%E8%83%A1%E6%AD%8C%E5%86%99%E7%9C%9F/"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.swsnqz-WSH5G-FVGStklEgHaKi&pid=15.1","pic_real_url":"http://himg2.huanqiu.com/attachment2010/2016/0921/20160921121416689.jpg","pic_from_url":"http://fashion.huanqiu.com/news/2016-09/9468424.html"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.3f05m8_1BhBpMvgvlXBBFgHaFj&pid=15.1","pic_real_url":"http://img.mp.itc.cn/upload/20160921/d7834061ff0745639896743298d635cb_th.png","pic_from_url":"http://yule.sohu.com/20160921/n468835536.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.q0XYN_Fxj9oROwdHZfcG_AHaLI&pid=15.1","pic_real_url":"http://img.mp.itc.cn/upload/20160921/c9e1be5fe3e94c4aa8628d3dd3a8914c_th.jpg","pic_from_url":"http://www.sohu.com/a/114749600_425340"}]}
     * error_code : 0
     * error_message : success
     */

    private Result result;
    @SerializedName("error_code")
    private String errorCode;
    @SerializedName("error_message")
    private String errorMessage;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String errorCode() {
        return errorCode;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }

    public static class Result {
        /**
         * module_type : 200
         * topic_type : 1006
         * pic_count : 35
         * pic_list : [{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.f7JJKSX2Vf5a0bJKSu1HnAHaEh&pid=15.1","pic_real_url":"http://www.mingxingku.com/Data/Images/Articles/2016-09-20/3199696607.jpg","pic_from_url":"http://www.mingxingku.com/photo-106434"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.ZfLuu0KFbHD1wv8IzuMxtwHaIG&pid=15.1","pic_real_url":"http://img.xiumu.cn/2016/0920/20160920093707476.jpg","pic_from_url":"http://www.xiumu.cn/yule/a/201609/177258.html"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.c1gbV_9jlmXCOiuan8AsbwHaJs&pid=15.1","pic_real_url":"http://www.mingxingku.com/Data/Images/Articles/2016-09-20/4284578295.jpg","pic_from_url":"http://www.mingxingku.com/photo-106434"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.Ii4AQY0Fay6evEEM9qfO5AHaE6&pid=15.1","pic_real_url":"http://img.mp.itc.cn/upload/20160921/0fe120084ab74881a916a81e1bf83ee5_th.jpg","pic_from_url":"http://www.sohu.com/a/114749600_425340"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.mKSz-B73GYbJBN-U_3yFQQHaE8&pid=15.1","pic_real_url":"http://m1.biz.itc.cn/pic/new/n/24/74/Img8717424_n.jpg","pic_from_url":"http://pic.yule.sohu.com/detail-755989-2.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.E0M8NQHed082fc3bva5wlQHaE6&pid=15.1","pic_real_url":"http://pic.vdfly.com/2016/0920/20160920025632164.jpg","pic_from_url":"http://news.vdfly.com/dujia/201609/561241.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.PiU9eyFK3aAzX-zAmovWAQAAAA&pid=15.1","pic_real_url":"http://images.china.cn/attachement/jpg/site1000/20161012/c03fd55e3b6d1967f1f54d.jpg","pic_from_url":"http://www.china.com.cn/ent/2016-10/12/content_39471329.htm"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.217RKKvGUFV9GhmL2dVnewHaJz&pid=15.1","pic_real_url":"http://www.mingxingku.com/Data/Images/Articles/2016-09-20/2718704659.jpg","pic_from_url":"http://www.mingxingku.com/star-744-picture"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.Ua6mMXSEl1LrMkFhv5CSdAHaKQ&pid=15.1","pic_real_url":"http://img4.cutv.com/images/2016/9/20/20169201474337874668_319.jpg","pic_from_url":"http://news.cutv.com/tupian/2016-9-20/1474338780167.shtml"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.sDnFYQJhlPz9X4GivNp8DgHaG9&pid=15.1","pic_real_url":"http://www.mingxingku.com/Data/Images/Articles/2016-09-21/2179090064.jpg","pic_from_url":"http://www.mingxingku.com/photo-106611"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.grFg1ZQ_sHW8eVAdBjJRewHaEK&pid=15.1","pic_real_url":"http://img1.3lian.com/2015/w22/22/d/101.jpg","pic_from_url":"http://www.xiaoshou.info/%E8%83%A1%E6%AD%8C%E5%86%99%E7%9C%9F/"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.ejLRqrrLXkk1LANVC1ulSAHaI1&pid=15.1","pic_real_url":"http://m4.biz.itc.cn/pic/new/n/43/62/Img8226243_n.jpg","pic_from_url":"http://www.xici.net/d226900575.htm"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.pHZnHMAtIiB6Hvv4Dlv2owAAAA&pid=15.1","pic_real_url":"http://m1.biz.itc.cn/pic/new/n/60/62/Img8226260_n.gif","pic_from_url":"http://www.xici.net/d226900575.htm"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP._UtI73PBNO2ChFPZiHyCtQHaEK&pid=15.1","pic_real_url":"http://img1.3lian.com/2015/w22/22/d/103.jpg","pic_from_url":"http://www.xiaoshou.info/%E8%83%A1%E6%AD%8C%E5%86%99%E7%9C%9F/"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.Jwk1NJ9IFN5I3X6ILN2RywHaI1&pid=15.1","pic_real_url":"http://m3.biz.itc.cn/pic/new/n/42/62/Img8226242_n.jpg","pic_from_url":"http://www.xici.net/d226900575.htm"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.n_c3jF7XH1i7ZygrQcrTwwAAAA&pid=15.1","pic_real_url":"http://www.86kx.com/uploads/allimg/160107/2295_160107163424_2.jpg","pic_from_url":"http://www.86kx.com/html/2016/0107/84645.html"},{"pic_thumbnail_url":"https://tse3-mm.cn.bing.net/th?id=OIP.P00RvEThRbLAWkas1qAuUAAAAA&pid=15.1","pic_real_url":"http://m2.biz.itc.cn/pic/new/n/61/62/Img8226261_n.gif","pic_from_url":"http://pic.yule.sohu.com/detail-706964-6.shtml"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.DrBnu_HYjwiuSuo6FmNjNQHaK3&pid=15.1","pic_real_url":"http://himg2.huanqiu.com/attachment2010/2016/0921/20160921121416604.jpg","pic_from_url":"http://fashion.huanqiu.com/news/2016-09/9468424.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.5SRoFyvm0EIDR02Zn_J-YQHaI1&pid=15.1","pic_real_url":"http://m1.biz.itc.cn/pic/new/n/44/62/Img8226244_n.jpg","pic_from_url":"http://pic.yule.sohu.com/detail-706964-3.shtml"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.LsmPsOXDkfotb-LPafisQQAAAA&pid=15.1","pic_real_url":"http://y2.ifengimg.com/ifengimcp/pic/20160107/e702e0917230257093a0_size1296_w396_h220.gif","pic_from_url":"http://www.52rkl.cn/funlaile/010G1213R016.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.XHCwRNJ-4u19JG-FKR47EwEgDY&pid=15.1","pic_real_url":"https://img3.yxlady.com/yl/UploadFiles_5361/20160921/20160921180224431.jpg","pic_from_url":"http://ent.yxlady.com/201609/1300684.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.OPdKY2o9aYz30jcMRbtTvAHaEO&pid=15.1","pic_real_url":"http://img2.jiemian.com/101/original/20151206/144936887615266100.jpg","pic_from_url":"http://www.jiemian.com/article/463598.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.5Rc3dV7uYoIj30oe-qlfjgHaE7&pid=15.1","pic_real_url":"http://www.hi.chinanews.com/hnnew/2016-09-20/U63P16T1D424019F8DT20160920162304.jpg","pic_from_url":"http://www.hi.chinanews.com/hnnew/2016-09-20/424019.html"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.3ESxLliZRybjeXtBKVRp4QHaJt&pid=15.1","pic_real_url":"http://img.mingxing.com/mingxing20180111/0f617ddf9a1baced3f4b7224d40c0be6.jpg","pic_from_url":"http://www.mingxing.com/news/index/id/323364.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.X6XMfrGXLvBL-dXl_TgRwwAAAA&pid=15.1","pic_real_url":"http://n.sinaimg.cn/ent/transform/500/w300h200/20190107/RqwM-hrfcctn5099981.jpg","pic_from_url":"http://ent.sina.com.cn/s/m/2019-01-06/doc-ihqhqcis3627444.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.okh-6EX29z4P9HfnxErIawAAAA&pid=15.1","pic_real_url":"https://img.4hw.com.cn/20190107/fe4ffe5a97d75ea889a4260df083a0ee.jpg!titlepic","pic_from_url":"https://yule.4hw.com.cn/news/201901/616_238837.html"},{"pic_thumbnail_url":"https://tse3-mm.cn.bing.net/th?id=OIP.7F7ITdtpbKxNpDZgAX5IdAHaE7&pid=15.1","pic_real_url":"http://upload.taihainet.com/2016/0921/1474416743606.jpg","pic_from_url":"http://www.taihainet.com/news/pastime/yllq/2016-09-21/1790921.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.tsLrn9yQ1M9vD9plpY0mMwAAAA&pid=15.1","pic_real_url":"http://y2.ifengimg.com/ifengimcp/pic/20160107/79c80989d2abf094c312_size816_w396_h220.gif","pic_from_url":"http://www.52rkl.cn/funlaile/010G1213R016.html"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.7utHnJ92d3pq6KPNyFVnQwAAAA&pid=15.1","pic_real_url":"https://2img.hitv.com/preview/cms_icon/2019/1/7/01/20190107110425777.jpg_366x206.jpg","pic_from_url":"https://www.mgtv.com/"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.J7AEycMqyMRBlSh_R4lGdAHaE8&pid=15.1","pic_real_url":"http://www.sd.chinanews.com/2/2016/0920/U415P935T2D24972F12DT20160920143415.jpg","pic_from_url":"http://www.sd.chinanews.com/2/2016/0920/24972.html"},{"pic_thumbnail_url":"https://tse2-mm.cn.bing.net/th?id=OIP.87DZd3SiVDzBHSkEglkN9wDMEy&pid=15.1","pic_real_url":"http://m4.biz.itc.cn/pic/new/n/07/74/Img8717407_n.jpg","pic_from_url":"http://pic.yule.sohu.com/detail-755989-25.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.rK2OtJCUwcHrMIK71MpIlAHaNJ&pid=15.1","pic_real_url":"http://d.5857.com/gg_160616/desk_001.jpg","pic_from_url":"http://www.xiaoshou.info/%E8%83%A1%E6%AD%8C%E5%86%99%E7%9C%9F/"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.swsnqz-WSH5G-FVGStklEgHaKi&pid=15.1","pic_real_url":"http://himg2.huanqiu.com/attachment2010/2016/0921/20160921121416689.jpg","pic_from_url":"http://fashion.huanqiu.com/news/2016-09/9468424.html"},{"pic_thumbnail_url":"https://tse1-mm.cn.bing.net/th?id=OIP.3f05m8_1BhBpMvgvlXBBFgHaFj&pid=15.1","pic_real_url":"http://img.mp.itc.cn/upload/20160921/d7834061ff0745639896743298d635cb_th.png","pic_from_url":"http://yule.sohu.com/20160921/n468835536.shtml"},{"pic_thumbnail_url":"https://tse4-mm.cn.bing.net/th?id=OIP.q0XYN_Fxj9oROwdHZfcG_AHaLI&pid=15.1","pic_real_url":"http://img.mp.itc.cn/upload/20160921/c9e1be5fe3e94c4aa8628d3dd3a8914c_th.jpg","pic_from_url":"http://www.sohu.com/a/114749600_425340"}]
         */

        @SerializedName("module_type")
        private String moduleType;
        @SerializedName("topic_type")
        private String topicType;
        @SerializedName("pic_count")
        private String pictureCount;
        @SerializedName("pic_list")
        private List<Picture> pictureList;

        public String getModuleType() {
            return moduleType;
        }

        public void setModuleType(String moduleType) {
            this.moduleType = moduleType;
        }

        public String getTopicType() {
            return topicType;
        }

        public void setTopicType(String topicType) {
            this.topicType = topicType;
        }

        public String getPictureCount() {
            return pictureCount;
        }

        public void setPictureCount(String pictureCount) {
            this.pictureCount = pictureCount;
        }

        public List<Picture> getPictureList() {
            return pictureList;
        }

        public void setPictureList(List<Picture> pictureList) {
            this.pictureList = pictureList;
        }

        public static class Picture {
            /**
             * pic_thumbnail_url : https://tse1-mm.cn.bing.net/th?id=OIP.f7JJKSX2Vf5a0bJKSu1HnAHaEh&pid=15.1
             * pic_real_url : http://www.mingxingku.com/Data/Images/Articles/2016-09-20/3199696607.jpg
             * pic_from_url : http://www.mingxingku.com/photo-106434
             */

            @SerializedName("pic_thumbnail_url")
            private String pictureThumbnailUrl;
            @SerializedName("pic_real_url")
            private String pictureRealUrl;
            @SerializedName("pic_from_url")
            private String pictureFromUrl;

            public String getPictureThumbnailUrl() {
                return pictureThumbnailUrl;
            }

            public void setPictureThumbnailUrl(String pictureThumbnailUrl) {
                this.pictureThumbnailUrl = pictureThumbnailUrl;
            }

            public String getPictureRealUrl() {
                return pictureRealUrl;
            }

            public void setPictureRealUrl(String pictureRealUrl) {
                this.pictureRealUrl = pictureRealUrl;
            }

            public String getPictureFromUrl() {
                return pictureFromUrl;
            }

            public void setPictureFromUrl(String pictureFromUrl) {
                this.pictureFromUrl = pictureFromUrl;
            }
        }
    }
}
