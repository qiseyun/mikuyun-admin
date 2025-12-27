package com.mikuyun.admin.support;

import com.mikuyun.admin.entity.document.PostDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/27 15:14
 */
@Slf4j
@Component
public class EsIndexInitializer {

    private final ElasticsearchOperations operations;

    public EsIndexInitializer(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createIndexIfNotExists() {
        var indexOps = operations.indexOps(PostDoc.class);
        if (!indexOps.exists()) {
            indexOps.create();
            log.info("Created Elasticsearch index: posts");
        }
    }

}
