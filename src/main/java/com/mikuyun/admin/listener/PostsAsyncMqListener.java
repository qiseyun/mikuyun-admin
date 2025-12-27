package com.mikuyun.admin.listener;

import com.alibaba.fastjson2.JSON;
import com.mikuyun.admin.entity.document.PostDoc;
import com.mikuyun.admin.es.PostsEsRepository;
import com.mikuyun.admin.mqRocket.IBaseMessageListener;
import com.mikuyun.admin.mqRocket.MqTopicEnum;
import com.mikuyun.admin.util.MqSerializationUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/27 15:18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostsAsyncMqListener implements IBaseMessageListener {

    private final PostsEsRepository postsEsRepository;

    @Override
    public String getTopic() {
        return MqTopicEnum.CANAL_SERVER.getRocketMqTopic();
    }

    @Override
    public String getTag() {
        return MqTopicEnum.CANAL_SERVER.getTag();
    }

    @Override
    public Boolean consumer(MessageExt message) {
        try {
            // 解析消息
            PostSyncEvent event = MqSerializationUtils.deserialize(message.getBody(), PostSyncEvent.class);
            log.info("收到文章同步消息: {}", JSON.toJSONString(event));
            if ("DELETE".equals(event.getType())) {
                postsEsRepository.deleteById(event.getId());
                log.info("ES 删除文章: {}", event.getId());
            } else if ("UPDATE".equals(event.getType()) || "INSERT".equals(event.getType())) {
                event.getData().forEach(item -> {
                    // INSERT / UPDATE：从 DB 查询最新数据(不推荐); 直接使用mq消息里的信息
                    if (item == null || !"2".equals(item.getStatus())) {
                        // 如果文章不存在或不是发布状态，从 ES 中删除（避免草稿被搜到）
                        postsEsRepository.deleteById(event.getId());
                        return;
                    }
                    item.setStatus("published");
                    postsEsRepository.save(item);
                    log.info("ES 同步文章; 文章id: {}", item.getId());
                });
            }
            return true;
        } catch (Exception e) {
            log.error("处理文章同步消息失败", e);
            return false;
        }
    }

    /**
     * 内部类：消息体
     */
    @Getter
    @Setter
    public static class PostSyncEvent {

        private Integer id;

        private List<PostDoc> data;
        /**
         * INSERT, UPDATE, DELETE
         */
        private String type;
    }

}
