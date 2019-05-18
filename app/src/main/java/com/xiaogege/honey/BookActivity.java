package com.xiaogege.honey;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaogege.honey.ui.Book;
import com.xiaogege.honey.utils.StringUtils;

import org.litepal.LitePal;

import java.util.List;

public class BookActivity extends AppCompatActivity {
    private TextView titleText;
    private TextView catalogText;
    private TextView tagsText;
    private TextView sub1Text;
    private TextView sub2Text;
    private TextView readingText;
    private TextView onLineText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow ().getDecorView ();
            decorView.setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow ().setStatusBarColor (Color.WHITE);
        }
        setContentView (R.layout.activity_book);
        titleText=findViewById (R.id.book_title);
        catalogText=findViewById (R.id.book_catalog);
        tagsText=findViewById (R.id.book_tags);
        sub1Text=findViewById (R.id.book_sub1);
        sub2Text=findViewById (R.id.book_sub2);
        readingText=findViewById (R.id.book_reading);
        onLineText=findViewById (R.id.book_online);
        Intent intent=getIntent ();
        String name=intent.getStringExtra ("name");
        if(name!=null){
            List<Book> books=LitePal.where ("title like ?",name).find (Book.class);
            if(books!=null&&books.size ()>0){
                Book book=books.get (0);
                titleText.setText ("书名: "+"\n"+"        "+StringUtils.normalize (book.getTitle ()));
                catalogText.setText ("类别: "+"\n"+"        "+StringUtils.normalize (book.getCatalog ()));
                tagsText.setText ("评价: "+"\n"+"        "+StringUtils.normalize (book.getTags ()));
                sub1Text.setText ("简介: "+"\n"+"        "+StringUtils.normalize (book.getSub1 ()));
                sub2Text.setText ("主要内容: "+"\n"+"        "+StringUtils.normalize (book.getSub2 ()));
                readingText.setText ("已读人次: "+"\n"+"        "+StringUtils.normalize (book.getReading ()));
                String onLine=book.getOnline ();
                StringBuilder builder=new StringBuilder ();
                String[] items=onLine.split (" ");
                for(int i=0;i<items.length;i++){
                    builder.append (String.valueOf (i+1)+". "+items[i]+"\n");
                }
                onLineText.setText ("购买地址: "+"\n"+builder.toString ());
            }
        }
    }
}
