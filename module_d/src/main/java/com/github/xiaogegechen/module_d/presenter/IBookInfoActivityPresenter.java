package com.github.xiaogegechen.module_d.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_d.model.BookInfoItem;
import com.github.xiaogegechen.module_d.model.db.BookInDB;
import com.github.xiaogegechen.module_d.view.IBookInfoActivityView;

import java.util.List;

public interface IBookInfoActivityPresenter extends IBasePresenter<IBookInfoActivityView> {
    /**
     * 把 BookInDB 对象转化为adapter需要的 BookInfoItem
     * @param book 待处理book对象
     * @return BookInfoItem 的list
     */
    List<BookInfoItem> convertBookInDB2BookInfoItem(BookInDB book);
}
