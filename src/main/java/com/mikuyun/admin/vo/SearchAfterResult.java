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

    private List<T> esContent;

    /**
     * 下一页游标
     */
    private String nextSearchAfter;

    private boolean hasNext;
}
