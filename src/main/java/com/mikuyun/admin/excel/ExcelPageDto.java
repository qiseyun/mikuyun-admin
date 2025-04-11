package com.mikuyun.admin.excel;

import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 21:25
 */
@Data
public class ExcelPageDto {

    private Integer offset;

    private Integer pageSize;

    private Integer pageNo;

    public void initPageParams(int pageNo, int pageSize) {
        this.offset = pageNo * pageSize;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

}
