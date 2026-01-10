package com.mikuyun.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/27 21:46
 */
@Data
public class SearchAfterResult<T> {

    /**
     * 列表数据
     */
    private List<T> esContent;

    /**
     * 下一页游标
     */
    private String nextSearchAfter;

    /**
     * 是否有下一页
     */
    private boolean hasNext;
}
