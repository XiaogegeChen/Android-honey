package com.github.xiaogegechen.module_left.helper;

import android.content.Context;

import com.github.xiaogegechen.common.util.XmlIOUtil;
import com.github.xiaogegechen.module_left.Constants;
import com.github.xiaogegechen.module_left.model.CityInfo;
import com.github.xiaogegechen.module_left.model.SelectedCity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 管理选择城市的操作，单例
 */
public class SelectedCitySetHelper {

    private static final String SPLITTER = "_";

    private static volatile SelectedCitySetHelper sInstance;

    private Context mApplicationContext;
    // 已经添加的城市列表，在单例实例化时从sp中读取，之后每次添加和删除都操作这个set，最后提交时将它重新写入sp。这样的主要
    // 目的是避免多次io，影响效率
    private Set<String> mSelectedCitySet;

    public static SelectedCitySetHelper getInstance(Context applicationContext) {
        if(sInstance == null){
            synchronized (SelectedCitySetHelper.class){
                if (sInstance == null) {
                    sInstance = new SelectedCitySetHelper(applicationContext);
                }
            }
        }
        return sInstance;
    }

    private SelectedCitySetHelper(Context applicationContext){
        mApplicationContext = applicationContext;
        // 从sp中读取
        mSelectedCitySet = XmlIOUtil.INSTANCE.readStringSet(
                Constants.XML_KEY_SELECTED_CITY_LIST_MODULE_LEFT,
                mApplicationContext
        );
        if(mSelectedCitySet == null){
            mSelectedCitySet = new HashSet<>();
        }
    }

    /**
     * 查询已经添加的城市列表
     *
     * @return 已经添加的城市列表，单条记录是连接的字符串，没有进行过处理.
     */
    public Set<String> getSelectedCitySet(){
        return mSelectedCitySet;
    }

    /**
     * 查询已经添加的城市列表
     * @return 已经添加的城市列表，单条记录是 SelectedCity 实例
     */
    public List<SelectedCity> getSelectedCity(){
        List<SelectedCity> result = new ArrayList<>();
        if(mSelectedCitySet.isEmpty()){
            return result;
        }
        for (String selectedCityString : mSelectedCitySet) {
            result.add(convertStringInSp2SelectedCity(selectedCityString));
        }
        return result;
    }

    /**
     * 判断是否有已经添加的城市，有则返回true，否则返回false
     *
     * @return 有则返回true，否则返回false
     */
    public boolean hasSelectedCity(){
        return !mSelectedCitySet.isEmpty();
    }

    /**
     * 判断一个城市是否已经被添加
     * @param cityId 城市代码
     * @param location 城市名称
     * @return 如果这个城市已经被添加，返回true，否则返回false
     */
    public boolean isCitySelected(String cityId, String location){
        return mSelectedCitySet.contains(encodeSelectedCity(cityId, location));
    }

    /**
     * 判断一个城市是否已经被添加
     * @param cityInfo 城市详情
     * @return 如果这个城市已经被添加，返回true，否则返回false
     */
    public boolean isCitySelected(CityInfo cityInfo){
        return isCitySelected(cityInfo.getCityId(), cityInfo.getLocation());
    }

    /**
     * 添加城市
     * @param cityId 城市代码
     * @param location 城市名称
     */
    public void addCity(String cityId, String location){
        mSelectedCitySet.add(encodeSelectedCity(cityId, location));
    }

    /**
     * 添加城市
     * @param cityInfo 城市详情
     */
    public void addCity(CityInfo cityInfo){
        addCity(cityInfo.getCityId(), cityInfo.getLocation());
    }

    /**
     * 移除城市
     * @param cityId 城市代码
     * @param location 城市名称
     */
    public void removeCity(String cityId, String location){
        mSelectedCitySet.remove(encodeSelectedCity(cityId, location));
    }

    /**
     * 移除城市
     * @param cityInfo 城市详情
     */
    public void removeCity(CityInfo cityInfo){
        removeCity(cityInfo.getCityId(), cityInfo.getLocation());
    }

    /**
     * 移除城市
     * @param selectedCity 已经添加的城市
     */
    public void removeCity(SelectedCity selectedCity){
        removeCity(selectedCity.getId(), selectedCity.getLocation());
    }

    /**
     * 提交操作，这个操作会替换掉原有的列表，将改动提交到sp中，因此在进行了增删或者批量增删之后一定要commit。
     */
    public void commit(){
        XmlIOUtil.INSTANCE.writeStringSet(
                Constants.XML_KEY_SELECTED_CITY_LIST_MODULE_LEFT,
                mSelectedCitySet,
                mApplicationContext
        );
    }

    /**
     * 将多个字符串连接为一个字符串.
     * sp中保存的已选中城市的格式为 cityId + "SPLITTER + location，比如北京是: CN101010100_北京
     *
     * @param cityId 城市id 如： CN101010100
     * @param location 城市名，如：北京
     * @return 连接后的字符，如：CN101010100_北京
     */
    private static String encodeSelectedCity(String cityId, String location){
        return cityId + SPLITTER + location;
    }

    /**
     * 将连接后的字符断开为原来的字符
     *
     * @param textFromSp 从sp中拿到的连接后的字符
     * @return 断开后的字符数组，按照写入的顺序排列。
     */
    private static String[] decodeSelectedCity(String textFromSp){
        return textFromSp.split(SPLITTER);
    }

    /**
     * 将从sp中读取到的拼接的字符串转化成 SelectedCity 对象
     * @param selectedCityStringInSp 从sp中读取的拼接的字符串
     * @return SelectedCity 实例
     */
    private static SelectedCity convertStringInSp2SelectedCity(String selectedCityStringInSp){
        String[] decodeSelectedCity = decodeSelectedCity(selectedCityStringInSp);
        String cityId = decodeSelectedCity[0];
        String location = decodeSelectedCity[1];
        SelectedCity selectedCity = new SelectedCity();
        selectedCity.setId(cityId);
        selectedCity.setLocation(location);
        return selectedCity;
    }
}
