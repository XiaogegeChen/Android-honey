package com.github.xiaogegechen.module_d.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * 存放图书目录的表，包括目录名（catalog）， 和目录id（catalogId）
 * 和一个自增id
 * 在插入时，只要是catalogId相同就需要覆盖原有数据
 */
@Entity(indexes = {
        @Index(value = "catalogId", unique = true)
})
public class CatalogInfoInDB {

    @Id
    private Long id;

    private String catalog;

    private int catalogId;

    public CatalogInfoInDB(){}

    public CatalogInfoInDB(String catalog, int catalogId) {
        this.catalog = catalog;
        this.catalogId = catalogId;
    }

    @Generated(hash = 1719402881)
    public CatalogInfoInDB(Long id, String catalog, int catalogId) {
        this.id = id;
        this.catalog = catalog;
        this.catalogId = catalogId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }
}
