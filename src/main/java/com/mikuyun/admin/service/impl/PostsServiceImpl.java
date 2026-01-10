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
import com.mikuyun.admin.mapper.PostsMapper;
import com.mikuyun.admin.service.IPostsService;
import com.mikuyun.admin.util.EsUtils;
import com.mikuyun.admin.vo.SearchAfterResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    @Override
    public void fullSyncToEs() {
        int pageSize = 1000;
        log.info("开始分页全量同步文章到 Elasticsearch...");
        int totalSynced = 0;
        // 最大 id
        Integer maxId = this.baseMapper.getMaxId();
        int maxPage = maxId / pageSize + 1;
        IndexCoordinates indexCoordinates = IndexCoordinates.of("posts");
        for (int i = 1; i <= maxPage; i++) {
            int startId = (i - 1) * pageSize;
            int endId = i * pageSize;
            // 分页数据
            List<PostDoc> postsList = this.baseMapper.fullSyncList(startId, endId);
            if (postsList.isEmpty()) {
                break;
            }
            // 存入 es
            elasticsearchTemplate.save(postsList, indexCoordinates);
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
        // 构建排序：1. 按相关性得分降序（让 boost 生效）; 2. 按 id 降序（用于打平和 search_after 稳定性）
        List<SortOptions> sortOptions = Arrays.asList(
                new SortOptions.Builder()
                        .score(s -> s.order(SortOrder.Desc))
                        .build(),
                new SortOptions.Builder()
                        .field(f -> f.field("id").order(SortOrder.Desc))
                        .build()
        );
        // 构建高亮设置 定义要高亮的字段及其选项 高亮 title excerpt 字段，设置前后缀、片段大小等
        List<HighlightField> highlightFields = Arrays.asList(
                new HighlightField("title", HighlightFieldParameters.builder()
                        .withPreTags("<mark>")
                        .withPostTags("</mark>")
                        .withFragmentSize(150)
                        .withNumberOfFragments(3)
                        .build()),
                // 高亮 excerpt 字段
                new HighlightField("excerpt", HighlightFieldParameters.builder()
                        .withPreTags("<mark>")
                        .withPostTags("</mark>")
                        .withFragmentSize(150)
                        .withNumberOfFragments(3)
                        .build()));
        // 构建查询
        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                .withQuery(queryBuilder)
                .withSort(sortOptions)
                .withHighlightQuery(new HighlightQuery(new Highlight(highlightFields), null))
                .withMaxResults(size);
        if (ObjectUtil.isNotEmpty(searchAfter)) {
            nativeQueryBuilder.withSearchAfter(Arrays.asList(EsUtils.decodeSearchAfterFromBase64(searchAfter)));
        }
        NativeQuery query = nativeQueryBuilder.build();
        SearchHits<PostDoc> hits = elasticsearchTemplate.search(query, PostDoc.class);
        // EsUtils.getDocSearchAfterResult(size, hits) // 原始文本返回;
        // 手动注入高亮内容
        List<PostDoc> highlightedContent = hits.getSearchHits().stream().map(hit -> {
            PostDoc doc = hit.getContent();
            // 高亮 title
            List<String> titleHighlights = hit.getHighlightField("title");
            if (!titleHighlights.isEmpty()) {
                doc.setTitle(titleHighlights.getFirst());
            }
            // 高亮 excerpt
            List<String> excerptHighlights = hit.getHighlightField("excerpt");
            if (!excerptHighlights.isEmpty()) {
                doc.setExcerpt(excerptHighlights.getFirst());
            }
            return doc;
        }).toList();
        // 构建结果（复用 EsUtils 的游标逻辑，但传入已高亮的内容）
        SearchAfterResult<PostDoc> result = new SearchAfterResult<>();
        boolean hasNext = !highlightedContent.isEmpty() && highlightedContent.size() == size;
        if (hasNext) {
            SearchHit<PostDoc> lastHit = hits.getSearchHits().getLast();
            result.setNextSearchAfter(EsUtils.encodeSearchAfterToBase64(lastHit.getSortValues().toArray()));
        }
        result.setEsContent(highlightedContent);
        result.setHasNext(hasNext);
        return result;
    }

}
