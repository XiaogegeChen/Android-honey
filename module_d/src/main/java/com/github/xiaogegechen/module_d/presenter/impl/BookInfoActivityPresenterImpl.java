package com.github.xiaogegechen.module_d.presenter.impl;

import com.github.xiaogegechen.module_d.model.BookInfoItem;
import com.github.xiaogegechen.module_d.model.db.BookInDB;
import com.github.xiaogegechen.module_d.presenter.IBookInfoActivityPresenter;
import com.github.xiaogegechen.module_d.view.IBookInfoActivityView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookInfoActivityPresenterImpl implements IBookInfoActivityPresenter {

    private IBookInfoActivityView mBookInfoActivityView;

    @Override
    public void attach(IBookInfoActivityView bookInfoActivityView) {
        mBookInfoActivityView = bookInfoActivityView;
    }

    @Override
    public void detach() {
        mBookInfoActivityView = null;
    }

    @Override
    public List<BookInfoItem> convertBookInDB2BookInfoItem(BookInDB book) {
        List<BookInfoItem> result = new ArrayList<>();
        result.add(new BookInfoItem("摘要", book.getSub1()));
        result.add(new BookInfoItem("主要内容", book.getSub2()));
        result.add(new BookInfoItem("评价", book.getTags()));
        result.add(new BookInfoItem("分类", book.getCatalog()));
        result.add(new BookInfoItem("已读人次", book.getReading()));
        result.add(new BookInfoItem("网购地址", formWebUrl(book.getOnline())));
        return result;
    }

    // 匹配中文
    private static Pattern sPattern = Pattern.compile("[\\u4e00-\\u9fa5]+");

    /**
     * 对拿到的网购地址格式规整， 比如：
     * "京东商城:http://book.jd.com/10483893.html 当当网:http://product.dangdang.com/product.aspx?product_id=21020821 苏宁易购:http://www.suning.com/emall/prd_10052_22001_-7_1006212_.html "
     * @param text 待规整的网购地址
     * @return 规范的网购地址
     */
    private static String formWebUrl(String text){
        try{
            // 匹配到的所有中文
            List<String> nameList = new ArrayList<>();
            Matcher matcher = sPattern.matcher(text);
            while (matcher.find()){
                nameList.add(matcher.group());
            }
            // 解析出url
            List<String> urlList = new ArrayList<>();
            for (int i = 0; i < nameList.size(); i++) {
                String s = nameList.get(i);
                int begin = text.indexOf(s.charAt(s.length() - 1)) + 2;
                int end;
                if(i == nameList.size() - 1){
                    end = text.length() - 1 ;
                }else{
                    end = text.indexOf(nameList.get(i + 1).charAt(0));
                }
                String res = text.substring(begin, end);
                urlList.add(res);
            }
            // 格式调整，每个商家和它的website都占单独一行
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < urlList.size(); i++) {
                String supplierName = nameList.get(i);
                String url = urlList.get(i);
                builder.append(i + 1);
                builder.append(". ");
                builder.append(supplierName);
                builder.append(" : ");
                builder.append(url);
                builder.append("\n");
            }
            return builder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return text;
    }
}
