package com.mikuyun.admin.service;

import com.mikuyun.admin.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.document.PostDoc;
import com.mikuyun.admin.vo.SearchAfterResult;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author mikuyun
 * @since 2025-12-27 14:54
 */
public interface IPostsService extends IService<Posts> {

    void fullSyncToEs();

    SearchAfterResult<PostDoc> findByTitleOrExcerpt(String keyword, String searchAfter, int size);

}
