package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author qiseyun
 * @since 2023-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qise_mq_msg_record")
@Schema(description = "")
@NoArgsConstructor
@AllArgsConstructor
public class MqMsgRecord {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "MQ消息id")
    private String msgId;

    @Schema(name = "MQ消息体")
    private String msgBody;

    @Schema(name = "消息状态(0:未消费  1:已消费)")
    private Integer status;

    @Schema(name = "创建时间")
    private LocalDateTime gmtCreated;

}
