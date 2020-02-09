package com.github.xiaogegechen.weather.model.db;

import androidx.annotation.IntDef;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 记录是否允许背景图改变的表，这个表永远只有一行一列，只存储一个int值。不要直接操作数据库，通过
 * {@link com.github.xiaogegechen.weather.helper.BooleanValueManager} 提供的API操作
 */
@Entity()
public class AllowBgChange {

    public static final int ALLOW = 0;
    public static final int DISALLOW = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            ALLOW,
            DISALLOW
    })
    public @interface BgChangeValue{
    }

    @Id
    private Long id;

    /**
     * 是否允许背景图改变，当取值为{@link #DISALLOW}时不允许背景图改变，当取值为{@link #ALLOW}时允许背景图改变
     */
    private int value;

    public AllowBgChange(){}

    @Generated(hash = 1899478146)
    public AllowBgChange(Long id, int value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(@BgChangeValue int value) {
        this.value = value;
    }
}
