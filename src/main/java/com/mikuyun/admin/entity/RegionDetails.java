package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 地区表
 * </p>
 *
 * @author qiseyun
 * @since 2025/03/02 22:03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("region_details")
@Schema(description = "地区表")
public class RegionDetails {

    @Schema(name = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "父id")
    private Integer pid;

    @Schema(name = "简称")
    private String shortname;

    @Schema(name = "名称")
    private String name;

    @Schema(name = "全称")
    private String mergename;

    @Schema(name = "层级 0 1 2 省市区县")
    private Integer level;

    @Schema(name = "拼音")
    private String pinyin;

    @Schema(name = "长途区号")
    private String code;

    @Schema(name = "邮编")
    private String zip;

    @Schema(name = "首字母")
    private String first;

    @Schema(name = "经度")
    private String lng;

    @Schema(name = "纬度")
    private String lat;


}
