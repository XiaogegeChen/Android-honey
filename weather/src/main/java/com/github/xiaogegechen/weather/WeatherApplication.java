package com.github.xiaogegechen.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.xiaogegechen.common.base.IApp;
import com.github.xiaogegechen.weather.model.db.DaoMaster;
import com.github.xiaogegechen.weather.model.db.DaoSession;

public class WeatherApplication implements IApp {
    // 用于操作数据库
    private static DaoSession sDaoSession;
    private Context mApplicationContext;

    @Override
    public void initContext(Context context) {
        mApplicationContext = context;
    }

    @Override
    public void initGreenDao() {
        // 建表
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mApplicationContext, "honey-weather");
        SQLiteDatabase database = helper.getWritableDatabase();
        sDaoSession = new DaoMaster(database).newSession();
    }

    public static DaoSession getDaoSession(){
        return sDaoSession;
    }
}
