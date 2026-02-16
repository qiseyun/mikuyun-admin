package com.mikuyun.admin.controller.demo;

import com.mikuyun.admin.common.R;
import com.mikuyun.admin.entity.document.PostDoc;
import com.mikuyun.admin.service.IPostsService;
import com.mikuyun.admin.vo.SearchAfterResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author mikuyun
 * @since 2025-12-27 14:54
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final IPostsService postsService;

    @GetMapping(value = "/sync")
    @Operation(summary = "es全量同步")
    public R<Void> syncPosts() {
        postsService.fullSyncToEs();
        return R.ok();
    }

    @GetMapping(value = "/test")
    @Operation(summary = "从es分页检索demo")
    public R<SearchAfterResult<PostDoc>> getList() {
        return R.ok(postsService.findByTitleOrExcerpt("okhttp3封装 虚拟线程", "", 5));
    }

}
