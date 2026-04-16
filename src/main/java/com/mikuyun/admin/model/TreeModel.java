package com.mikuyun.admin.model;

import java.util.List;

/**
 * @author mikuyun
 * @since 2024/3/22 17:34
 */
public interface TreeModel<T> {

    Object getId();

    Object getPid();

    Long getDeep();

    Long getSort();

    List<T> getChildren();

    void setChildren(List<T> children);

}
