package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author mikuyun
 * @since 2025-12-27 14:54
 */
@Getter
@Setter
@ToString
@TableName("mk_posts")
@Accessors(chain = true)
@Schema(name = "Posts", description = "文章表")
public class Posts extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作者ID
     */
    @Schema(description = "作者ID")
    private Integer userId;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String title;

    /**
     * 封面
     */
    @Schema(description = "封面")
    private String cover;

    /**
     * 文章内容（Markdown或HTML）
     */
    @Schema(description = "文章内容（Markdown或HTML）")
    private String content;

    /**
     * 文章摘要
     */
    @Schema(description = "文章摘要")
    private String excerpt;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 浏览次数
     */
    @Schema(description = "浏览次数")
    private Integer viewCount;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间")
    private LocalDateTime publishedAt;

}
