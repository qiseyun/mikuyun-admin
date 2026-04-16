package com.mikuyun.admin.es;

import com.mikuyun.admin.entity.document.PostDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikuyun
 * @since 2025/12/27 15:56
 */
@Repository
public interface PostsEsRepository extends ElasticsearchRepository<PostDoc, Integer> {

}
