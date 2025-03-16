package com.mikuyun.admin.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年12月23日/0023 22点24分
 */
@Data
public class BaseEntity {

    /**
     * 逻辑删除:0：正常 1：删除
     */
    private Integer isDelete;

    /**
     * 创建者编号
     */
    private Integer createBy;

    /**
     * 修改者
     */
    private Integer updateBy;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreated;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

}
