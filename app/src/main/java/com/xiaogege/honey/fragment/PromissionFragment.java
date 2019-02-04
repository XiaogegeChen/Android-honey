package com.xiaogege.honey.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaogege.honey.BookActivity;
import com.xiaogege.honey.R;
import com.xiaogege.honey.RequestBookListener;
import com.xiaogege.honey.RequestBookTask;
import com.xiaogege.honey.ui.Book;
import com.xiaogege.honey.utils.HttpUtils;

import org.angmarch.views.NiceSpinner;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PromissionFragment extends MyFragment{
    private ImageView arrowImage;
    private NiceSpinner translateFromSelection;
    private NiceSpinner translateToSelection;
    private Button translateButton;
    private EditText translateInput;
    private TextView translateOutput;
    private String translateQuery="";
    private String fromLang="auto";
    private String toLang="zh";
    private String translateResponse;
    private static final int TRANSLATE=0;
    private static final String[] LangZh={"自动检测", "中文", "英语", "粤语", "文言文","日语", "韩语", "法语", "西班牙语",
            "泰语","阿拉伯语", "俄语", "葡萄牙语", "德语", "意大利语","希腊语", "荷兰语", "波兰语", "保加利亚语", "爱沙尼亚语",
            "丹麦语", "芬兰语", "捷克语", "罗马尼亚语", "斯洛文尼亚语", "瑞典语","匈牙利语", "繁体中文", "越南语"};
    private static final String[] LangEn={"auto","zh", "en","yue", "wyw", "jp", "kor", "fra", "spa","th", "ara",
            "ru", "pt", "de", "it", "el", "nl", "pl", "bul", "est", "dan", "fin", "cs", "rom", "slo", "swe", "hu", "cht", "vie"};

    private TextView bookTitle1;
    private TextView bookTitle2;
    private TextView bookTitle3;
    private TextView bookTitle4;
    private TextView bookTitle5;
    private List<TextView> bookTitleList=new ArrayList<> ();
    private Button bookRefreshButton;
    private int bookIdIndex=0;
    private List<Book> bookList=new ArrayList<> ();
    private boolean bookLoadDone=false;
    private RequestBookTask task;
    private RequestBookListener listener=new RequestBookListener () {
        @Override
        public void onFailed() {
            Toast.makeText (getContext (),"图书数据获取失败,可能是网络未开启或者今日更新次数已达上限(100次)",Toast.LENGTH_SHORT).show ();
            setBookTitlesToDefault ();
        }

        @Override
        public void onSuccess(List<Book> books) {
            bookLoadDone=true;
            bookList.addAll (books);
            LitePal.deleteAll (Book.class);
            for(Book book:bookList){
                book.save ();
            }
            bookIdIndex=0;
            setBookTitle ();
        }
    };

    private Handler handler=new Handler () {
        @Override
        public void handleMessage(Message message) {
            switch (message.what){
                case TRANSLATE:
                    String result=HttpUtils.handleTranslateResult (translateResponse);
                    if(result!=null){
                        translateOutput.setText ("翻译结果:"+"\n"+result);
                    }else{
                        translateOutput.setText ("翻译结果:"+"\n"+"翻译失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        LitePal.initialize (getContext ());
        List<Book> books=LitePal.findAll (Book.class);
        if(books.size ()<=0){
            LitePal.getDatabase ();
            bookLoadDone=false;
            task=new RequestBookTask (listener);
            task.execute ();
        }else{
            bookList.addAll (books);
            bookLoadDone=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate (R.layout.promission_fragment_layout,container,false);
        translateInit (view);
        bookInit (view);
        setContent ();
        translateFromSelection.addOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fromLang=LangEn[position];
            }
        });
        translateToSelection.addOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toLang=LangEn[position+1];
            }
        });
        translateButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(translateInput.getText ()!=null){
                    translateQuery=translateInput.getText ().toString ();
                    requestTranslateResult ();
                }
            }
        });
        bookRefreshButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                setBookTitle ();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated (savedInstanceState);
        setBookTitle ();
    }

    //初始化百度翻译的各个控件
    private void translateInit(View view){
        arrowImage=view.findViewById (R.id.arrow_image_view);
        translateFromSelection=view.findViewById (R.id.from_nice_spinner);
        translateToSelection=view.findViewById (R.id.to_nice_spinner);
        translateInput=view.findViewById (R.id.translate_edit_text_input);
        translateOutput=view.findViewById (R.id.translate_output_text);
        translateButton=view.findViewById (R.id.translate);
        arrowImage.setImageResource (R.drawable.arrow_xiaocao);
    }

    //为两个下拉框适配内容
    private void setContent(){
        List<String> fromLangList=new ArrayList<> ();
        for(int i=0;i<LangZh.length;i++){
            fromLangList.add (LangZh[i]);
        }
        translateFromSelection.attachDataSource (fromLangList);
        List<String> toLangList=new ArrayList<> ();
        toLangList.addAll (fromLangList);
        toLangList.remove (0);
        translateToSelection.attachDataSource (toLangList);
    }

    //查询翻译的结果
    private void requestTranslateResult(){
        String address=HttpUtils.getTranslateURL(translateQuery,fromLang,toLang);
        HttpUtils.sendRequest (address, new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity ().runOnUiThread (new Runnable () {
                    @Override
                    public void run() {
                        Toast.makeText (getContext (),"翻译失败，请检查网络是否已启用",Toast.LENGTH_SHORT).show ();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body ().string ();
                translateResponse = responseText;
                Message message = new Message ();
                message.what = TRANSLATE;
                handler.sendMessage (message);
            }
        });
    }

    //初始化每日图书的各个控件
    private void bookInit(View view){
        bookTitle1=view.findViewById (R.id.book_1);
        bookTitle2=view.findViewById (R.id.book_2);
        bookTitle3=view.findViewById (R.id.book_3);
        bookTitle4=view.findViewById (R.id.book_4);
        bookTitle5=view.findViewById (R.id.book_5);
        bookRefreshButton=view.findViewById (R.id.book_refresh);
        bookTitleList.add(bookTitle1);
        bookTitleList.add(bookTitle2);
        bookTitleList.add(bookTitle3);
        bookTitleList.add(bookTitle4);
        bookTitleList.add(bookTitle5);
        setBookTitle ();
    }

    //数据回滚方法
    private int getRealId(int id,int size,boolean toDefault){
        toDefault=false;
        if(id<size){
            return id;
        }else{
            toDefault=true;
            return id-size;
        }
    }

    //设置五个文本框的内容
    private void setBookTitle(){
        if(bookLoadDone){
            List<Book> books=new ArrayList<> ();
            int allBooksSize=LitePal.findAll (Book.class).size ();
            boolean toDefault=false;
            for(int i=bookIdIndex;i<bookIdIndex+5;i++){
                Book book=LitePal.find (Book.class,getRealId (i,allBooksSize,toDefault));
                books.add (book);
            }
            if(toDefault){
                bookIdIndex=0;
            }else{
                bookIdIndex+=5;
            }
            for(int i=0;i<5;i++){
                String content="红旗飘飘";
                final String name;
                if(books.get(i)!=null){
                    content=books.get (i).getTitle ();
                    name=content;
                }else{
                    name=null;
                }
                bookTitleList.get(i).setText (content);
                bookTitleList.get(i).setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent (getContext (),BookActivity.class);
                        intent.putExtra ("name",name);
                        startActivity (intent);
                    }
                });
            }
        }else{
            setBookTitlesToDefault ();
        }
    }

    private void setBookTitlesToDefault(){
        for(TextView textView:bookTitleList){
            textView.setText ("红旗飘飘");
            textView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent (getContext (),BookActivity.class);
                    startActivity (intent);
                }
            });
        }
    }
}
