package com.xiaogege.honey;

import com.xiaogege.honey.ui.Book;

import java.util.List;

public interface RequestBookListener {
    void onFailed();
    void onSuccess(List<Book> bookList);
}
