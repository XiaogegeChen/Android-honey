package com.xiaogege.honey;

import android.os.AsyncTask;

import com.xiaogege.honey.sentence.MySentence;
import com.xiaogege.honey.ui.Book;
import com.xiaogege.honey.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestBookTask extends AsyncTask<Void,Void,Boolean> {
    private RequestBookListener listener;
    private List<Book> bookList;

    public RequestBookTask(RequestBookListener listener){
        this.listener=listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        bookList=new ArrayList<> ();
        try{
            for(String id:MySentence.BOOKID){
                String address=MySentence.BOOK+id;
                OkHttpClient client=new OkHttpClient ();
                Request request=new Request.Builder ().url(address).build ();
                Response response=client.newCall (request).execute ();
                String responseText=response.body ().string ();
                List<Book> books=HttpUtils.handleBookResult (responseText);
                if(books==null){
                    return false;
                }else{
                    bookList.addAll (books);
                }
            }
        }catch(IOException e){
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean status){
        if(status){
            listener.onSuccess (bookList);
        }else{
            listener.onFailed ();
        }
    }
}
