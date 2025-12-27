package com.mikuyun.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.Posts;
import com.mikuyun.admin.entity.document.PostDoc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author mikuyun
 * @since 2025-12-27 14:54
 */
public interface PostsMapper extends BaseMapper<Posts> {

    Integer getMaxId();

    PostDoc getPostById(Integer id);

    List<PostDoc> fullSyncList(@Param("startId") Integer startId, @Param("endId") Integer endId);

}
