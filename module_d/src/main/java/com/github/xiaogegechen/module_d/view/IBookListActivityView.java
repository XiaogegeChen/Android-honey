package com.github.xiaogegechen.module_d.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_d.model.CatalogInfo;
import com.github.xiaogegechen.module_d.model.db.BookInDB;

import java.util.List;

public interface IBookListActivityView extends IBaseView {
    /**
     * 显示图书目录
     * @param catalogInfoList 图书目录集合
     */
    void showCatalog(List<CatalogInfo> catalogInfoList);

    /**
     * 显示图书列表
     * @param bookList 图书列表
     */
    void showBookList(List<BookInDB> bookList);

    /**
     * 隐藏加载中提示
     */
    void hideProgress();

    /**
     * 隐藏加载失败提示
     */
    void hideErrorPage();
}
