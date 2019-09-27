package com.github.xiaogegechen.module_b.model;

/**
 * 将response需要统一处理的bean归为一类
 */
public interface Catchable {
    /**
     * 拿到errorCode
     * @return errorCode
     */
    String errorCode();
}
