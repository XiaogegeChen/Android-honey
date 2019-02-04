package com.xiaogege.honey.utils;

import com.google.gson.Gson;
import com.xiaogege.honey.sentence.MySentence;
import com.xiaogege.honey.ui.Book;
import com.xiaogege.honey.ui.Express;
import com.xiaogege.honey.ui.ExpressList;
import com.xiaogege.honey.ui.MojieToday;
import com.xiaogege.honey.ui.MojieWeek;
import com.xiaogege.honey.ui.MojieYear;
import com.xiaogege.honey.ui.QQTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtils {
    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /**
     *OKhttp的请求函数
     */
    public static void sendRequest(String address, Callback callback){
        OkHttpClient client=new OkHttpClient ();
        Request request=new Request.Builder ().url (address).build ();
        client.newCall (request).enqueue (callback);
    }

    /**
     *获得星座api的后半段
     */
    public static String urlencode(Map<String,Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     *处理星座api返回的当天数据，存储在MojieToday实例中
     */
    public static MojieToday handleMojieTodayResponse(String response){
        return new Gson ().fromJson (response,MojieToday.class);
    }

    /**
     *处理星座api返回的本周数据，存储在MojieWeek实例中
     */
    public static MojieWeek handleMojieWeekResponse(String response){
        return new Gson().fromJson (response,MojieWeek.class);
    }

    /**
     *处理星座api返回的年度数据，存储在MojieYear对象中，如果解析失败返回null
     */
    public static MojieYear handleMojieYearResponse(String response){
        MojieYear mojieYear=new MojieYear ();
        try{
            JSONObject jsonObject=new JSONObject (response);
            mojieYear.setData (jsonObject.getString("date"));
            mojieYear.setAll (jsonObject.getJSONObject ("mima").getString ("info"));
            mojieYear.setReadme (jsonObject.getJSONObject ("mima").getJSONArray ("text").get(0).toString ());
            mojieYear.setCareer (jsonObject.getJSONArray ("career").get(0).toString ());
            mojieYear.setLove (jsonObject.getJSONArray ("love").get(0).toString ());
            mojieYear.setHealth (jsonObject.getJSONArray ("health").get(0).toString ());
            mojieYear.setFinance (jsonObject.getJSONArray ("finance").get(0).toString ());
            mojieYear.setResultCode (jsonObject.getString ("resultcode"));
            mojieYear.setErrorCode (jsonObject.getString ("error_code"));
        }catch(JSONException e){
            return null;
        }
        return mojieYear;
    }

    /**
     *处理qq测试吉凶api返回的数据，存储在QQTest对象中，如果解析失败返回null
     */
    public static QQTest handleQQTestResponse(String response){
        QQTest qqTest=new QQTest ();
        try{
            JSONObject jsonObject=new JSONObject (response);
            qqTest.setAnalysis (jsonObject.getJSONObject ("result").getJSONObject ("data").getString ("analysis"));
            qqTest.setConclusion (jsonObject.getJSONObject ("result").getJSONObject ("data").getString ("conclusion"));
            qqTest.setErrorCode (jsonObject.getString ("error_code"));
            qqTest.setReason (jsonObject.getString ("reason"));
        }catch(JSONException e){
            return null;
        }
        return qqTest;
    }

    /**
     * 处理周公解梦api返回的数据，取出梦境详情并存放在字符串数组中整合成字符串,如果解析失败返回"解析失败"
     */
    public static String handleDreamResponse(String response){
        List<String> list=new ArrayList<> ();
        try{
            JSONObject jsonObject=new JSONObject (response);
            String resultTest=jsonObject.getString ("result");
            if(resultTest=="null"){
                return "解梦结果: "+"\n"+"暂无相关信息，可以换个关键字测试哦!";
            }else{
                String result=jsonObject.getJSONArray ("result").get(0).toString ();
                JSONArray resultList=new JSONObject (result).getJSONArray ("list");
                for(int i=0;i<resultList.length ();i++){
                    list.add (resultList.get (i).toString ());
                }
            }
        }catch(JSONException e){
            e.printStackTrace ();
            return "解梦结果: "+"\n"+"解析失败";
        }
        StringBuilder builder=new StringBuilder ();
        builder.append ("解梦结果: "+"\n");
        for(int i=0;i<list.size ();i++){
            builder.append (String.valueOf (i+1)+"."+list.get (i)+"\n");
        }
        return builder.toString ();
    }

    /**
     * 处理百度翻译返回的JSON字符串，如果出错返回null
     * @param response 返回的JSON字符串
     * @return 翻译后的结果
     */
    public static String handleTranslateResult(String response){
        if(response==null){
            return null;
        }else{
            try{
                StringBuilder builder=new StringBuilder ();
                JSONObject jsonObject=new JSONObject (response);
                JSONArray jsonArray=jsonObject.getJSONArray ("trans_result");
                for(int i=0;i<jsonArray.length ();i++){
                    JSONObject jsonObject1=new JSONObject (jsonArray.get(i).toString ());
                    String dst=jsonObject1.getString ("dst");
                    builder.append (dst).append ("\n");
                }
                return builder.toString ();
            }catch(JSONException e){
                return null;
            }
        }
    }

    /**
     *处理查询图书api返回的数据，如果成功返回Book集合，如果失败返回null
     * @param response 返回的JSON字符串
     * @return 返回处理后的数据，成功返回Book集合，失败返回null
     */
    public static List<Book> handleBookResult(String response){
        List<Book> bookList=new ArrayList<> ();
        if(response==null){
            return null;
        }else{
            try{
                JSONArray jsonArray=new JSONObject (response).getJSONObject ("result").getJSONArray ("data");
                for(int i=0;i<jsonArray.length ();i++){
                    Book book=new Book ();
                    JSONObject jsonObject=new JSONObject (jsonArray.get(i).toString ());
                    book.setTitle (jsonObject.getString ("title"));
                    book.setCatalog (jsonObject.getString ("catalog"));
                    book.setTags (jsonObject.getString ("tags"));
                    book.setSub1 (jsonObject.getString ("sub1"));
                    book.setSub2 (jsonObject.getString ("sub2"));
                    book.setImg (jsonObject.getString ("img"));
                    book.setOnline (jsonObject.getString ("online"));
                    book.setReading (jsonObject.getString ("reading"));
                    book.setBytime (jsonObject.getString ("bytime"));
                    book.setCanBeSee (true);
                    bookList.add (book);
                }
            }catch(JSONException e){
                e.printStackTrace ();
                return null;
            }
        }
        return bookList;
    }

    /**
     * 获取聚合数据接口api返回值的错误码
     */
    public static String getMojieErrorCode(String response){
        String errorCode=null;
        try{
            JSONObject jsonObject=new JSONObject (response);
            errorCode=jsonObject.getString("error_code");
        }catch(JSONException e){
            e.printStackTrace ();
            return null;
        }
        return errorCode;
    }

    /**
     * 获得百度翻译的请求地址
     * @param query 请求的内容
     * @param from 源语种
     * @param to 翻译后的语种
     * @return  请求地址
     */
    public static String getTranslateURL(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return MySentence.TRANSLATE+urlencodeBaidu (params);
    }

    /**
     * 获得快递的status字段，失败返回null
     * @param response JSON数据
     * @return 快递的status字段
     */
    public static String getExpressStatus(String response){
        String status=null;
        try{
            JSONObject jsonObject=new JSONObject (response);
            status=jsonObject.getString ("status");
        }catch (JSONException e){
            e.printStackTrace ();
            return null;
        }
        return status;
    }

    /**
     * 获得快递的msg字段，失败返回null
     * @param response JSON数据
     * @return 快递的msg字段
     */
    public static String getExpressMsg(String response){
        String message=null;
        try{
            JSONObject jsonObject=new JSONObject (response);
            message=jsonObject.getString ("msg");
        }catch (JSONException e){
            e.printStackTrace ();
            return null;
        }
        return message;
    }

    /**
     * 处理快递查询的JSON字符串，并返回一个包含快递信息的快递的实例，出错返回null
     * @param response JSON字符串
     * @return 包含快递信息的快递的实例
     */
    public static Express handleExpressResult(String response){
        Express express=new Express ();
        try{
            JSONObject jsonObject=new JSONObject (response).getJSONObject ("result");
            express.setCourierl (jsonObject.getString ("courier"));
            express.setCourierPhone (jsonObject.getString ("courierPhone"));
            express.setDeliverystatus (jsonObject.getString ("deliverystatus"));
            express.setExpName (jsonObject.getString ("expName"));
            express.setExpPhone (jsonObject.getString ("expPhone"));
            express.setExpSite (jsonObject.getString ("expSite"));
            express.setIssign (jsonObject.getString ("issign"));
            express.setNumber (jsonObject.getString ("number"));
            express.setType (jsonObject.getString ("type"));
            JSONArray jsonArray=jsonObject.getJSONArray ("list");
            List<ExpressList> expressList=new ArrayList<> ();
            for(int i=0;i<jsonArray.length ();i++){
                ExpressList el=new ExpressList ();
                JSONObject jsonObject1=new JSONObject (jsonArray.get (i).toString ());
                el.setStatus (jsonObject1.getString ("status"));
                el.setTime (jsonObject1.getString ("time"));
                expressList.add(el);
            }
            express.setExpressList (expressList);
        }catch (JSONException e){
            e.printStackTrace ();
            return null;
        }
        return express;
    }

    /**
     * @param query 请求的内容
     * @param from 源语言
     * @param to 翻译后的语种
     * @return 请求地址中所有要求的参数的键值对
     */
    private  static Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<> ();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", MySentence.TRANSLATE_APPID);
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        String src = MySentence.TRANSLATE_APPID + query + salt + MySentence.TRANSLATE_KEY;
        params.put("sign", md5(src));
        return params;
    }

    /**
     * @param input 需要加密的文本字符串
     * @return md5加密后的字符串
     */
    private static String md5(String input) {
        if (input == null)
            return null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = input.getBytes("utf-8");
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }catch(UnsupportedEncodingException e){
            return null;
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    private static String urlencodeBaidu(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
