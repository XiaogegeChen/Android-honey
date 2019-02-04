package com.xiaogege.honey.sentence;

public class MySentence {
    /**
     *星座查询相关参数（查询限制，每天最多100次）
     *CONSTELLATION-----请求头
     *CONSTELLATION_KEY-----身份密钥
     */
    public static final String CONSTELLATION="http://web.juhe.cn:8080/constellation/getAll?";
    public static final String CONSTELLATION_KEY="76e853cb07c75138cf58ccaf765cc63e";

    /**
     *QQ号码测试吉凶的api，直接在后面拼接qq号码即可（查询限制，每天最多100次）
     */
    public static final String QQ="http://japi.juhe.cn/qqevaluate/qq?key=847e6d738979360de3b297f568c39be2&qq=";

    /**
     *周公解梦的api相关参数（查询限制，每天最多100次）
     */
    public static final String DREAM="http://v.juhe.cn/dream/query?";
    public static final String DREAM_KEY="4c5d1f08482c881cdcbc8adcda71e5f2";

    /**
     * 百度翻译api的相关参数(无限制调用)
     */
    public static final String TRANSLATE="http://api.fanyi.baidu.com/api/trans/vip/translate?";
    public static final String TRANSLATE_APPID="20190113000256501";
    public static final String TRANSLATE_KEY="fYQx1lat2NS_OnXUvyf6";

    /**
     * 多看好书图书api相关参数(查询限制，每天最多100次)，直接在地址后接id值
     */
    private static final String BOOK_KEY="1aeba37d12156308bc6ee789044677c8";
    public static final String BOOK="http://apis.juhe.cn/goodbook/query?key="+BOOK_KEY+"&pn=0&rn=30&catalog_id=";
    public static final String[] BOOKID={"242","252","244","248","257","243","247","251","253","250","249","245","256","254","246","255","258"};

    /**
     * 阿里云快递查询api相关参数(查询限制，2100次，有效期一年)
     */
    public static final String EXPRESS="http://wuliu.market.alicloudapi.com/kdi?no=";
    private static final String EXPRESS_APPCODE="5ae256b0a96e45f5a96a4d8bd2f57294";
    public static final String EXPRESS_KEY="25632473";
    public static final String EXPRESS_APPSECRET="2e3f80e5a7c980703b9761cd1a0fd277";
    public static final String AUTHORIZATION="APPCODE "+EXPRESS_APPCODE;

    /**
     * 图片下面配的描述
     */
    public static final String[] PICTUREDESCRIPTION={
            "爱你最好的方式，就是把你写进代码里",
            "我饮过最烈的酒,是你低头噙着笑的温柔",
            "这世界车水马龙,我只对你情有独钟",
            "我是个坏脾气的人，却还是可以容忍你",
            "用一千年的时间去爱你，再用一万年的时间去忘记",
            "拉着你的手，无论是在哪里，我都感觉像是朝天堂奔跑",
            "天塌下来，我陪你看天崩地裂",
            "我愿意轻轻地把你拖起，一如托起生活的一切重担",
            "陪你这条路有多远,我就走多远",
            "当岁月老去，我依然记得曾经夕阳下我们美好的誓言",
            "让我们坐在时间的长河边，望远絮絮细语，吟唱那人生之歌",
            "世界很大，难得遇见你，我愿永远这样高调的爱着你",
            "答案很长，我准备用一生的时间来回答，你准备要听了吗",
            "我不在乎浮光掠影的尘世，我只在乎你",
            "我不留恋似雪若花的浮生，我只留恋你",
            "时间冲不淡真情的酒，距离拉不开思念的手。想你，直到地老天荒，直到永久",
            "无论多大风多大雨，我都去接你"
    };

    /**
     * about中的文本
     */
    public static final String CONTENT_ABOUT_HEAD="        这可是爸爸只为你做的APP,每一行代码都是爸爸亲手敲进去的,是不是想夸爸爸厉害?" +
            "坐下坐下!!!(手动滑稽和狗头，懒得贴表情了,嘿嘿嘿)"+"\n\n"+"        接下来?当然是紧张刺激的骚话环节啦!"+"\n\n"+"某小姐姐: 为什么" +
            "要做张广草男朋友?"+"\n"+"某正正小哥哥: 肯定要做啦,不做她就没有男朋友啦!"+"\n"+"某小姐姐: 那你不会去找别的小姐姐吗?"+"\n"+"某正正小哥哥: " +
            "找别的小姐姐是是不可能找别的小姐姐的,这辈子都不可能找别的小姐姐的。别的小姐姐我又不喜欢,就是喜欢张广草,才能开心生活这样子"+"\n"+"某小姐姐: " +
            "那你觉得我好还是她好?"+"\n"+"某正正小哥哥: 跟她在一起就像回家一样,我跟你一起,打死我都不跟你一起,就你给我很多钱,我就跟你一起这样子,跟她" +
            "在一起的感觉,比和你好多了!"+"\n"+"某小姐姐: 为什么?"+"\n"+"某正正小哥哥: 跟你说话很无聊,都没有朋友,女朋友玩,跟她在一起说话又好听,我超喜欢" +
            "她的"+"\n\n"+"        接下来?肯定是情话环节啦!"+"\n\n";
    public static final String CONTENT_ABOUT_FOOT="\n"+"        哼,想我了吧,还不快点找几句情话给我!!!";
    public static final String CONTENT_GITHUB="哼,还不快去github上给爸爸点一个star"+"\n"+"项目github地址:"+"\n"+"https://github.com/zhengzhengxiaogege/Android-honey";
    public static final String CONTENT_FUNCTION="(一). 照片墙"+"\n"+"        里面有各种可耐的照片呦!可耐的小宝贝儿,可耐的大宝贝儿,可耐的风景和美食,以及可耐的表情包!点击一下还可以看大图那!(现在是小图，下个版本就有了!嘻嘻嘻)"+"\n\n"+
            "(二). 最懂你"+"\n"+"        都说了最懂你啦,这么迷信,就不信你不喜欢看星座(下拉就能刷新,当日的运势每天更新一次,本周的运势一周更新一次," +
            "年度运势每年更新一次,没事别老是刷新,每天就能刷新100次,用完就没了!)。爸爸还准备了QQ号测吉凶(输入QQ号点击\"开始测试\"就可以看了,也是每天100次,别想着" +
            "靠这个赚钱,知道吧!),还有周公解梦(输入梦境关键字,点击\"开始查询\"就可以了,100次!记住了吧!)"+"\n\n"+"(三). 快学习"+"\n"+"        为了监督你学习，不让你在家闲的发慌" +
            ",爸爸给你添加了好书推荐和翻译的功能,没事多翻翻看看里面的书,知道了吧!点击一下书名就可以查看书本的详情了呦!翻译功能可是能翻译20多种语言的呢!爸爸不介意你吧所有语言的" +
            "\"我爱你\"都发给我呦!"+"\n\n"+"(四). 小助手"+"\n"+"        本来想在里面添加很多有用的功能的,但是因为没时间了,就添加了一个快递查询的功能!输入快递单号就可以查看快递当前的状态" +
            "但是你今年只能查询2100次,这2100次可是爸爸在阿里云上画8.41元买的,好好珍惜，知道吧(狗头)!用没了还得续费呢!"+"\n\n"+"(五). 相关信息"+"\n"+"        既然你都看见" +
            "这个了,肯定就知道怎么打开相关信息这一栏了!算了,还是再说一下吧!在任意一个界面从左边手机屏幕外面向右滑,才是开启这里的正确姿势,在屏幕内左右滑动是切换界面的" +
            ",这里面就是一些相关的东西,每个APP都有,你的肯定也不能少,是吧!";
    public static final String CONTENT_FEEDBACK="臭居居,还想加什么功能,快告诉爸爸,有空就给你加上,没空就算了吧(手动狗头,怕被打死，吼吼吼!)";
    public static final String CONTENT_HELP="版本信息: "+"\n"+"版本号: V1.0"+"\n\n"+"        每年一版"+"\n"+"        今年如此"+"\n"+"        年年皆然!!!";
}
