package com.github.xiaogegechen.weather.helper;

import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.WeatherApplication;
import com.github.xiaogegechen.weather.model.db.AllowBgChange;
import com.github.xiaogegechen.weather.model.db.AllowBgChangeDao;

import java.util.List;

/**
 * bool值管理类，单例
 */
public class BooleanValueManager {
    private static volatile BooleanValueManager sInstance;

    public static BooleanValueManager getInstance() {
        if (sInstance == null) {
            synchronized (BooleanValueManager.class){
                if (sInstance == null) {
                    sInstance = new BooleanValueManager();
                }
            }
        }
        return sInstance;
    }

    private AllowBgChangeDao mAllowBgChangeDao;

    private BooleanValueManager(){
        mAllowBgChangeDao = WeatherApplication.getDaoSession().getAllowBgChangeDao();
    }

    public void setAllowBgChange(boolean allow){
        List<AllowBgChange> list = mAllowBgChangeDao.queryBuilder()
                .build()
                .list();
        int value = allow ? AllowBgChange.ALLOW : AllowBgChange.DISALLOW;
        if(list.size() == 0){
            // 存进去一行
            AllowBgChange allowBgChange = new AllowBgChange();
            allowBgChange.setValue(value);
            mAllowBgChangeDao.insertOrReplace(allowBgChange);
        }else{
            // 如果值改变，则更新
            AllowBgChange allowBgChange = list.get(0);
            if(value != allowBgChange.getValue()){
                allowBgChange.setValue(value);
                mAllowBgChangeDao.save(allowBgChange);
            }
        }
    }

    public boolean isAllowBgChange(){
        List<AllowBgChange> list = mAllowBgChangeDao.queryBuilder()
                .build()
                .list();
        if(list.size() == 0){
            // 现在还没有数据，返回默认值
            return Constants.ALLOW_BG_CHANGE;
        }else{
            AllowBgChange allowBgChange = list.get(0);
            return allowBgChange.getValue() == AllowBgChange.ALLOW;
        }
    }
}
