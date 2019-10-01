package com.github.xiaogegechen.module_d.model;

/**
 * 目录详情的bean，包括一个目录名和它的id
 */
public class CatalogInfo {
    // 目录名
    private String catalog;
    // 目录id
    private int id;

    public CatalogInfo(){}

    public CatalogInfo(String catalog, int id) {
        this.catalog = catalog;
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public int getId() {
        return id;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setId(int id) {
        this.id = id;
    }
}
