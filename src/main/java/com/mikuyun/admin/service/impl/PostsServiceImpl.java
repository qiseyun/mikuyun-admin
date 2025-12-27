package com.mikuyun.admin.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.Posts;
import com.mikuyun.admin.entity.document.PostDoc;
import com.mikuyun.admin.es.PostsEsRepository;
import com.mikuyun.admin.mapper.PostsMapper;
import com.mikuyun.admin.service.IPostsService;
import com.mikuyun.admin.util.EsUtils;
import com.mikuyun.admin.vo.SearchAfterResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2025-12-27 14:54
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

    private final ElasticsearchTemplate elasticsearchTemplate;

    private final PostsEsRepository postsEsRepository;

    @Override
    public void fullSyncToEs() {
        int pageSize = 10;
        log.info("开始分页全量同步文章到 Elasticsearch...");
        int totalSynced = 0;
        // 最大id
        Integer maxId = this.baseMapper.getMaxId();
        int maxPage = maxId / pageSize + 1;
        for (int i = 1; i <= maxPage; i++) {
            int startId = (i - 1) * pageSize;
            int endId = i * pageSize;
            // 分页数据
            List<PostDoc> postsList = this.baseMapper.fullSyncList(startId, endId);
            if (postsList.isEmpty()) {
                break;
            }
            // 存入es
            postsEsRepository.saveAll(postsList);
            // 记录同步条数
            totalSynced += postsList.size();
            log.info("已同步 {} 条文章", totalSynced);
            ThreadUtil.sleep(200);
        }
        log.info("全量同步完成，共同步 {} 篇文章", totalSynced);
    }

    @Override
    public SearchAfterResult<PostDoc> findByTitleOrExcerpt(String keyword, String searchAfter, int size) {
        // 安全限制
        if (size <= 0) size = 10;
        if (size > 1000) size = 1000;
        // 构建查询条件
        Query queryBuilder;
        if (StrUtil.isBlank(keyword)) {
            // 空关键词 → match_all
            queryBuilder = new MatchAllQuery.Builder().build()._toQuery();
        } else {
            // 非空关键词 → 原有 bool + should 逻辑
            queryBuilder = new BoolQuery.Builder()
                    .should(s -> s.match(m -> m.field("title").query(keyword)))
                    .should(s -> s.match(m -> m.field("excerpt").query(keyword)))
                    .build()._toQuery();
        }
        // 构建排序：必须和 searchAfter 的字段顺序一致！
        List<SortOptions> sortOptions = Collections.singletonList(
                // 主排序：id 降序
                new SortOptions.Builder()
                        .field(f -> f.field("id").order(SortOrder.Desc))
                        .build()
        );
        // 构建查询
        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                .withQuery(queryBuilder)
                .withSort(sortOptions)
                .withMaxResults(size);
        if (ObjectUtil.isNotEmpty(searchAfter)) {
            nativeQueryBuilder.withSearchAfter(Arrays.asList(EsUtils.decodeSearchAfterFromBase64(searchAfter)));
        }
        NativeQuery query = nativeQueryBuilder.build();
        SearchHits<PostDoc> hits = elasticsearchTemplate.search(query, PostDoc.class);
        // 准备下一页游标
        return EsUtils.getDocSearchAfterResult(size, hits);
    }

}
