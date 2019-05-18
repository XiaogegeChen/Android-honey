package com.xiaogege.honey.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaogege.honey.R;
import com.xiaogege.honey.sentence.MySentence;
import com.xiaogege.honey.ui.MojieToday;
import com.xiaogege.honey.ui.MojieWeek;
import com.xiaogege.honey.ui.MojieYear;
import com.xiaogege.honey.ui.QQTest;
import com.xiaogege.honey.utils.HttpUtils;
import com.xiaogege.honey.utils.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class QQSpaceFragment extends Fragment {

    private static final int UPDATE_DREAM=1;
    private static final int UPDATE_QQ=0;
    private SwipeRefreshLayout refreshLayout;
    private TextView dataText;
    private TextView allText;
    private TextView colorText;
    private TextView healthText;
    private TextView loveText;
    private TextView moneyText;
    private TextView numberText;
    private TextView qFriendText;
    private TextView workText;
    private TextView summaryText;
    private ImageView todayImage;
    private TextView todayUpdateTimeText;

    private TextView dataTextWeek;
    private TextView loveTextWeek;
    private TextView moneyTextWeek;
    private TextView allTextWeek;
    private TextView workTextWeek;
    private ImageView weekImage;

    private TextView dataTextYear;
    private TextView allTextYear;
    private TextView readmeTextYear;
    private TextView careerTextYear;
    private TextView loveTextYear;
    private TextView healthTextYear;
    private TextView financeTextYear;
    private ImageView yearImage;

    private TextView qqConclusion;
    private TextView qqAnalysis;
    private EditText qqEditText;
    private Button qqTestButton;

    private EditText dreamEditText;
    private Button dreamButton;
    private TextView dreamResultText;

    private String responseTextToday;
    private String responseTextWeek;
    private String responseTextYear;
    private String responseQQTest;
    private String responseDream;

    private static final String TAG="QQSpaceFragment";
    private Handler handler=new Handler (){
        @Override
        public void handleMessage(Message message){
            switch(message.what){
                case UPDATE_DREAM:
                    dreamResultText.setText("");
                    if(responseDream!=null){
                        dreamResultText.setText (HttpUtils.handleDreamResponse (responseDream));
                    }else{
                        dreamResultText.setText("解梦结果: "+"\n"+"____");
                    }
                    break;
                case UPDATE_QQ:
                    qqAnalysis.setText ("结论分析: "+"\n"+"");
                    qqConclusion.setText ("测试结论: "+"\n"+"");
                    if(responseQQTest!=null){
                        QQTest qqTest=HttpUtils.handleQQTestResponse (responseQQTest);
                        qqAnalysis.setText ("结论分析: "+"\n"+StringUtils.normalize (qqTest.getAnalysis ()));
                        qqConclusion.setText ("测试结论: "+"\n"+StringUtils.normalize (qqTest.getConclusion ()));
                    }else{
                        QQTest qqTest=new QQTest ();
                        qqAnalysis.setText ("结论分析: "+"\n"+StringUtils.normalize (qqTest.getAnalysis ()));
                        qqConclusion.setText ("测试结论: "+"\n"+StringUtils.normalize (qqTest.getConclusion ()));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences (getContext ());
        String responseJSONToday=preferences.getString ("response_today",null);
        String responseJSONWeek=preferences.getString ("response_week",null);
        String responseJSONYear=preferences.getString ("response_year",null);
        if(responseJSONToday!=null){
            responseTextToday=responseJSONToday;
        }else{
            requestConstellationMojieToday ();
        }
        if(responseJSONWeek!=null){
            responseTextWeek=responseJSONWeek;
        }else{
            requestConstellationMojieWeek ();
        }
        if(responseJSONYear!=null){
            responseTextYear=responseJSONYear;
        }else{
            requestConstellationMojieYear ();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate (R.layout.qq_space_fragment_layout,container,false);
        todayInit (view);
        weekInit (view);
        yearInit (view);
        qqTestInit (view);
        dreamInit (view);
        todayImage.setImageResource (R.drawable.mojie_today);
        weekImage.setImageResource (R.drawable.mojie_week);
        yearImage.setImageResource (R.drawable.mojie_year);
        refreshLayout.setColorSchemeColors (Color.RED);

        qqTestSetData ();
        dreamSetData ();
        if(responseTextToday!=null){
            MojieToday mojieToday=HttpUtils.handleMojieTodayResponse (responseTextToday);
            if(mojieToday!=null){
                todaySetData (mojieToday);
            }else{
                MojieToday mojieToday1=new MojieToday ();
                todaySetData (mojieToday1);
            }
        }else{
            MojieToday mojieToday=new MojieToday ();
            todaySetData (mojieToday);
        }
        if(responseTextWeek!=null){
            MojieWeek mojieWeek=HttpUtils.handleMojieWeekResponse (responseTextWeek);
            if(mojieWeek!=null){
                weekSetData (mojieWeek);
            }else{
                MojieWeek mojieWeek1=new MojieWeek ();
                weekSetData (mojieWeek1);
            }
        }else{
            MojieWeek mojieWeek=new MojieWeek ();
            weekSetData (mojieWeek);
        }
        if(responseTextYear!=null){
            MojieYear mojieYear=HttpUtils.handleMojieYearResponse (responseTextYear);
            if(mojieYear!=null){
                yearSetData (mojieYear);
            }else{
                MojieYear mojieYear1=new MojieYear ();
                yearSetData (mojieYear1);
            }
        }else{
            MojieYear mojieYear=new MojieYear ();
            yearSetData (mojieYear);
        }

        refreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                requestConstellationMojieToday ();
                requestConstellationMojieWeek ();
                requestConstellationMojieYear ();

                if(responseTextToday!=null){
                    MojieToday mojieToday=HttpUtils.handleMojieTodayResponse (responseTextToday);
                    if(mojieToday!=null){
                        todaySetData (mojieToday);
                    }else{
                        MojieToday mojieToday1=new MojieToday ();
                        todaySetData (mojieToday1);
                    }
                }else{
                    MojieToday mojieToday=new MojieToday ();
                    todaySetData (mojieToday);
                }
                if(responseTextWeek!=null){
                    MojieWeek mojieWeek=HttpUtils.handleMojieWeekResponse (responseTextWeek);
                    if(mojieWeek!=null){
                        weekSetData (mojieWeek);
                    }else{
                        MojieWeek mojieWeek1=new MojieWeek ();
                        weekSetData (mojieWeek1);
                    }
                }else{
                    MojieWeek mojieWeek=new MojieWeek ();
                    weekSetData (mojieWeek);
                }
                if(responseTextYear!=null){
                    MojieYear mojieYear=HttpUtils.handleMojieYearResponse (responseTextYear);
                    if(mojieYear!=null){
                        yearSetData (mojieYear);
                    }else{
                        MojieYear mojieYear1=new MojieYear ();
                        yearSetData (mojieYear1);
                        Toast.makeText (getContext (),"刷新失败，可能已经超过上限100次或是未知错误",Toast.LENGTH_SHORT).show ();
                    }
                }else{
                    MojieYear mojieYear=new MojieYear ();
                    yearSetData (mojieYear);
                    Toast.makeText (getContext (),"刷新失败，可能未连接网络或是已经超过上限100次或是未知错误",Toast.LENGTH_SHORT).show ();
                }
                Date date=new Date ();
                todayUpdateTimeText.setText (date.toString ().split (" ")[3]);
                refreshLayout.setRefreshing (false);
            }
        });
        qqTestButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(!StringUtils.isAllNumber (qqEditText.getText ().toString ())){
                    Toast.makeText (getContext (),"请输入正确的QQ号码",Toast.LENGTH_SHORT).show ();
                }else{
                    responseQQTest=null;
                    requestQQTest ();
                }
            }
        });
        dreamButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                responseDream=null;
                requestDream ();
            }
        });
        return view;
    }

    //查询摩羯座的今日运势
    private void requestConstellationMojieToday(){
        Map params = new HashMap ();
        params.put("key",MySentence.CONSTELLATION_KEY);
        params.put("consName","摩羯座");
        params.put("type","today");
        String address=MySentence.CONSTELLATION+HttpUtils.urlencode (params);
        HttpUtils.sendRequest (address, new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body ().string ();
                if("0".equals (HttpUtils.getMojieErrorCode (responseText))){
                    responseTextToday=responseText;
                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (getActivity ()).edit ();
                    editor.putString ("response_today",responseTextToday);
                    editor.apply ();
                }
            }
        });
    }

    //初始化今日运势的各个控件
    private void todayInit(View view){
        refreshLayout=view.findViewById (R.id.qq_space_swipe_refresh);
        todayImage=view.findViewById (R.id.mojie_today_image);
        dataText=view.findViewById (R.id.mojie_today_data_text);
        colorText=view.findViewById (R.id.mojie_today_color_text);
        healthText=view.findViewById (R.id.mojie_today_health_text);
        loveText=view.findViewById (R.id.mojie_today_love_text);
        moneyText=view.findViewById (R.id.mojie_today_money_text);
        numberText=view.findViewById (R.id.mojie_today_number_text);
        qFriendText=view.findViewById (R.id.mojie_today_q_friend_text);
        workText=view.findViewById (R.id.mojie_today_work_text);
        summaryText=view.findViewById (R.id.mojie_today_summary_text);
        allText=view.findViewById (R.id.mojie_today_all_text);
        todayUpdateTimeText=view.findViewById (R.id.mojie_today_refresh_text);
    }

    //今日运势设置内容
    private void todaySetData(MojieToday mojieToday){
        if(mojieToday!=null){
            dataText.setText (StringUtils.normalize (mojieToday.getData ()));
            colorText.setText (StringUtils.normalize (mojieToday.getColor ()));
            healthText.setText (StringUtils.normalize (mojieToday.getHealth ()));
            loveText.setText (StringUtils.normalize(mojieToday.getLove ()));
            moneyText.setText (StringUtils.normalize(mojieToday.getMoney ()));
            numberText.setText (StringUtils.normalize(mojieToday.getNumber ()));
            qFriendText.setText (StringUtils.normalize(mojieToday.getqFriend ()));
            workText.setText (StringUtils.normalize(mojieToday.getWork ()));
            summaryText.setText ("今日概述:"+StringUtils.normalize(mojieToday.getSummary ()));
            //summaryText.setText (responseTextToday);
            allText.setText (StringUtils.normalize (mojieToday.getAll ()));
        }
    }

    //查询摩羯座的本周运势
    private void requestConstellationMojieWeek(){
        Map params = new HashMap ();
        params.put("key",MySentence.CONSTELLATION_KEY);
        params.put("consName","摩羯座");
        params.put("type","week");
        String address=MySentence.CONSTELLATION+HttpUtils.urlencode (params);
        HttpUtils.sendRequest (address, new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body ().string ();
                if("0".equals (HttpUtils.getMojieErrorCode (responseText))){
                    responseTextWeek=responseText;
                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (getActivity ()).edit ();
                    editor.putString ("response_week",responseTextWeek);
                    editor.apply ();
                }
            }
        });
    }

    //初始化本周运势各个控件
    private void weekInit(View view){
        allTextWeek=view.findViewById (R.id.mojie_week_all_text);
        dataTextWeek=view.findViewById (R.id.mojie_week_data_text);
        moneyTextWeek=view.findViewById (R.id.mojie_week_money_text);
        loveTextWeek=view.findViewById (R.id.mojie_week_love_text);
        workTextWeek=view.findViewById (R.id.mojie_week_work_text);
        weekImage=view.findViewById (R.id.mojie_week_image);
    }

    //设置本周运势内容
    private void weekSetData(MojieWeek mojieWeek){
        dataTextWeek.setText (StringUtils.normalize(mojieWeek.getData ()));
        moneyTextWeek.setText (StringUtils.normalize(mojieWeek.getMoney ()));
        loveTextWeek.setText (StringUtils.normalize(mojieWeek.getLove ()));
        workTextWeek.setText (StringUtils.normalize(mojieWeek.getWork ()));
        allTextWeek.setText ("本周是今年的第"+StringUtils.normalize(mojieWeek.getAll ())+"周");
    }

    //初始化今年运势各个控件
    private void yearInit(View view){
        yearImage=view.findViewById (R.id.mojie_year_image);
        dataTextYear=view.findViewById (R.id.mojie_year_data_text);
        allTextYear=view.findViewById (R.id.mojie_year_all_text);
        readmeTextYear=view.findViewById (R.id.mojie_year_readme_text);
        careerTextYear=view.findViewById (R.id.mojie_year_career_text);
        loveTextYear=view.findViewById (R.id.mojie_year_love_text);
        healthTextYear=view.findViewById (R.id.mojie_year_health_text);
        financeTextYear=view.findViewById (R.id.mojie_year_finance_text);
    }

    //设置今年运势内容
    private void yearSetData(MojieYear mojieYear){
        dataTextYear.setText ("今年是"+StringUtils.normalize(mojieYear.getData ()));
        allTextYear.setText ("年度密码: "+StringUtils.normalize(mojieYear.getAll ()));
        readmeTextYear.setText ("年度概述: "+StringUtils.normalize(mojieYear.getReadme ()));
        careerTextYear.setText ("事业运: "+StringUtils.normalize(mojieYear.getCareer ()));
        loveTextYear.setText ("感情运: "+StringUtils.normalize(mojieYear.getLove ()));
        healthTextYear.setText ("健康运: "+StringUtils.normalize(mojieYear.getHealth ()));
        financeTextYear.setText ("财运: "+StringUtils.normalize(mojieYear.getFinance ()));
    }

    //查询摩羯座今年运势
    private void requestConstellationMojieYear(){
        Map params = new HashMap ();
        params.put("key",MySentence.CONSTELLATION_KEY);
        params.put("consName","摩羯座");
        params.put("type","year");
        String address=MySentence.CONSTELLATION+HttpUtils.urlencode (params);
        HttpUtils.sendRequest (address, new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity ().runOnUiThread (new Runnable () {
                    @Override
                    public void run() {
                        Toast.makeText (getContext (),"刷新失败，请检查网络是否已启用",Toast.LENGTH_SHORT).show ();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body ().string ();
                if("0".equals (HttpUtils.getMojieErrorCode (responseText))){
                    responseTextYear=responseText;
                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (getActivity ()).edit ();
                    editor.putString ("response_year",responseTextYear);
                    editor.apply ();
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (getContext (),"星座数据已更新",Toast.LENGTH_SHORT).show ();
                        }
                    });
                }
            }
        });
    }

    //初始化qq测试的各个控件
    private void qqTestInit(View view){
        qqAnalysis=view.findViewById (R.id.qq_analysis);
        qqConclusion=view.findViewById (R.id.qq_conclusion);
        qqEditText=view.findViewById (R.id.qq_edit_text);
        qqTestButton=view.findViewById (R.id.qq_button);
    }

    //设置qq测吉凶内容
    private void qqTestSetData(){
        qqAnalysis.setText ("结论分析: "+"\n");
        qqConclusion.setText ("测试结论: "+"\n");
    }

    //查询输入的qq号测试得到的内容
    private void requestQQTest(){
        //String qq=qqEditText.getText ().toString ();
        String qq=null;
        if(qqEditText.getText ()!=null){
            qq=qqEditText.getText ().toString ();
        }
        if(qq==null){
            Toast.makeText (getContext (),"测试的QQ号码不能为空",Toast.LENGTH_SHORT).show ();
        }else{
            String address=MySentence.QQ+qq;
            HttpUtils.sendRequest (address, new Callback () {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (getContext (),"测试失败，请检查网络是否已启用",Toast.LENGTH_SHORT).show ();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText=response.body ().string ();
                    if("0".equals (HttpUtils.getMojieErrorCode (responseText))){
                        responseQQTest=responseText;
                        Message message=new Message ();
                        message.what=UPDATE_QQ;
                        handler.sendMessage (message);
                    }else if("10012".equals (HttpUtils.getMojieErrorCode (responseText))){
                        getActivity ().runOnUiThread (new Runnable () {
                            @Override
                            public void run() {
                                Toast.makeText (getContext (),"今日测试次数已达上限(100次)，明天再来测试吧!",Toast.LENGTH_SHORT).show ();
                            }
                        });
                    }
                }
            });
        }
    }

    //初始化周公解梦的各个控件
    private void dreamInit(View view){
        dreamButton=view.findViewById (R.id.dream_button);
        dreamEditText=view.findViewById (R.id.dream_edit_text);
        dreamResultText=view.findViewById (R.id.dream_result);
    }

    //为周公解梦设置内容
    private void dreamSetData(){
        String resultText="解梦结果: "+"\n";
        dreamResultText.setText (resultText);
    }

    //查询输入的梦境获取的内容
    private void requestDream(){
        String dream=null;
        if(dreamEditText.getText ()!=null){
            dream=dreamEditText.getText ().toString ();
        }
        if(dream==null){
            Toast.makeText (getContext (),"待查询的梦境不能为空",Toast.LENGTH_SHORT).show ();
        }else{
            Map params = new HashMap ();
            params.put("key",MySentence.DREAM_KEY);
            params.put("q",dream);
            params.put("full","1");
            String address=MySentence.DREAM+HttpUtils.urlencode (params);
            Log.d (TAG,address);
            HttpUtils.sendRequest (address, new Callback () {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (getContext (),"测试失败，请检查网络是否已启用",Toast.LENGTH_SHORT).show ();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText=response.body ().string ();
                    Log.d (TAG,responseText);
                    if("0".equals (HttpUtils.getMojieErrorCode (responseText))){
                        responseDream=responseText;
                        Message message=new Message ();
                        message.what=UPDATE_DREAM;
                        handler.sendMessage (message);
                    }else if("10012".equals (HttpUtils.getMojieErrorCode (responseText))){
                        getActivity ().runOnUiThread (new Runnable () {
                            @Override
                            public void run() {
                                Toast.makeText (getContext (),"今日查询次数已达上限(100次)，明天再来测试吧!",Toast.LENGTH_SHORT).show ();
                            }
                        });
                    }
                }
            });
        }
    }
}
