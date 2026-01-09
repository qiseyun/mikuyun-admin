package com.mikuyun.admin.entity.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

/**
 * @author mikuyun
 * @version 1.0
 * @date 2025/12/27 15:12
 *
 * replicas 单机设置 0 集群 1
 */
@Data
@Document(indexName = "posts", dynamic = Dynamic.STRICT, createIndex = false, writeTypeHint = WriteTypeHint.FALSE)
@Setting(replicas = 0)
public class PostDoc {

    @Id
    private Integer id;

    @Field(type = FieldType.Integer)
    private Integer userId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String excerpt;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Keyword)
    private String cover;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedAt;

}
