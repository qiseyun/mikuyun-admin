package com.mikuyun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.Posts;
import com.mikuyun.admin.entity.document.PostDoc;
import com.mikuyun.admin.vo.SearchAfterResult;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author mikuyun
 * @since 2025-12-27 14:54
 */
public interface IPostsService extends IService<Posts> {

    /**
     * 全量同步
     */
    void fullSyncToEs();

    /**
     * 从es搜索
     *
     * @param keyword     关键字
     * @param searchAfter 当夜最后一条数据
     * @param size        每页数量
     * @return SearchAfterResult<PostDoc>
     */
    SearchAfterResult<PostDoc> findByTitleOrExcerpt(String keyword, String searchAfter, int size);

}
