package com.xiaogege.honey.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaogege.honey.R;
import com.xiaogege.honey.sentence.MySentence;
import com.xiaogege.honey.ui.Express;
import com.xiaogege.honey.ui.ExpressList;
import com.xiaogege.honey.utils.HttpUtils;
import com.xiaogege.honey.utils.StringUtils;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherFragment extends Fragment {
    private EditText expressInput;
    private ListView expressOutput;
    private ArrayAdapter<String> expressAdapter;
    private List<String> expressOutputList=new ArrayList<> ();
    private Button expressRequest;
    private String expressResponse;
    private static final int EXPRESS=0;
    private Handler handler=new Handler (){
        @Override
        public void handleMessage(Message message){
            switch (message.what){
                case EXPRESS:
                    if(expressResponse!=null){
                        Express express=HttpUtils.handleExpressResult (expressResponse);
                        if(express!=null){
                            setExpressOutputListData(express);
                            expressAdapter.notifyDataSetChanged ();
                            expressOutput.setSelection (0);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate (R.layout.weather_fragmnet_layout,container,false);
        expressInit (view);
        expressAdapter=new ArrayAdapter<> (getContext (),android.R.layout.simple_list_item_1,expressOutputList);
        expressOutput.setAdapter (expressAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);
        expressRequest.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String no=expressInput.getText ().toString ();
                if(!StringUtils.isAllNumber (no)){
                    Toast.makeText (getContext (),"快递单号错误",Toast.LENGTH_SHORT).show ();
                }else{
                    expressOutputList.clear ();
                    queryExpressInfo(no);
                }
            }
        });
    }

    //初始化快递查询的控件
    private void expressInit(View view){
        expressInput=view.findViewById (R.id.express_edit_text_input);
        expressOutput=view.findViewById (R.id.express_list_view);
        expressRequest=view.findViewById (R.id.express_request);
    }

    //查询快递信息
    private void queryExpressInfo(String no){
        String address=MySentence.EXPRESS+no;
        OkHttpClient client=new OkHttpClient ();
        Request request=new Request.Builder ().url(address).addHeader ("Authorization",MySentence.AUTHORIZATION).build ();
        client.newCall (request).enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity ().runOnUiThread (new Runnable () {
                    @Override
                    public void run() {
                        Toast.makeText (getContext (),"获取快递信息失败，请检查网络并重新刷新",Toast.LENGTH_SHORT).show ();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body ().string ();
                if("0".equals (HttpUtils.getExpressStatus (responseText))){
                    expressResponse=responseText;
                    Message message=new Message ();
                    message.what=EXPRESS;
                    handler.sendMessage (message);
                }else if("201".equals (HttpUtils.getExpressStatus (responseText))){
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (getContext (),"快递单号错误",Toast.LENGTH_SHORT).show ();
                        }
                    });
                }else if("203".equals (HttpUtils.getExpressStatus (responseText))){
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (getContext (),"快递公司不存在",Toast.LENGTH_SHORT).show ();
                        }
                    });
                }else if("204".equals (HttpUtils.getExpressStatus (responseText))){
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (getContext (),"快递公司识别失败",Toast.LENGTH_SHORT).show ();
                        }
                    });
                } else if("205".equals (HttpUtils.getExpressStatus (responseText))){
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (getContext (),"没有信息",Toast.LENGTH_SHORT).show ();
                        }
                    });
                }
            }
        });
    }

    //根据Express类设置适配器的数据源
    private void setExpressOutputListData(Express express){
        String number="快递单号: "+"\n"+"        "+express.getNumber ();
        String expName="快递公司: "+"\n"+"        "+express.getExpName ();
        String courier="快递员: "+"\n"+"        "+express.getCourierl ();
        String courierPhone="快递员手机号: "+"\n"+"        "+express.getCourierPhone ();
        String deliverystatus="";
        switch (express.getDeliverystatus ()){
            case "1":
                deliverystatus="在途中";
                break;
            case "2":
                deliverystatus="正在派件";
                break;
            case "3":
                deliverystatus="已签收";
                break;
            case "4":
                deliverystatus="派送失败";
                break;
            case "5":
                deliverystatus="疑难件";
                break;
            case "6":
                deliverystatus="退件签收";
                break;
            default:
                break;
        }
        deliverystatus="当前状态: "+"\n"+"        "+deliverystatus;
        String expSite="快递公司网址: "+"\n"+"        "+express.getExpSite ();
        String expPhone="快递公司电话: "+"\n"+"        "+express.getExpPhone ();
        String info="详细物流信息:";
        expressOutputList.add (number);
        expressOutputList.add (expName);
        expressOutputList.add(courier);
        expressOutputList.add(courierPhone);
        expressOutputList.add(deliverystatus);
        expressOutputList.add(expSite);
        expressOutputList.add(expPhone);
        expressOutputList.add(info);
        for(ExpressList el:express.getExpressList ()){
            String timeInfo="   "+"物流信息更新时间:"+"\n"+"        "+el.getTime ()+"\n\n"+"   "+"物流状态:"+"\n"+"        "+el.getStatus ();
            expressOutputList.add (timeInfo);
        }
    }
}
