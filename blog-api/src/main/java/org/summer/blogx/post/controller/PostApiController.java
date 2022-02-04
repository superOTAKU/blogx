package org.summer.blogx.post.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.summer.blogx.infrastructure.pagination.PageReq;
import org.summer.blogx.post.dto.PostDetailResp;
import org.summer.blogx.post.dto.PostListResp;
import org.summer.blogx.post.service.PostService;

import javax.validation.Valid;

/**
 * 提供对外可访问的API接口
 */
@PreAuthorize("permitAll()")
@Validated
@RestController
@RequestMapping("/api/post")
public class PostApiController {

    @Autowired
    private PostService service;

    @GetMapping
    public IPage<PostListResp> getPage(@Valid PageReq req) {
        return service.getPage(req);
    }

    @GetMapping("/{id}")
    public PostDetailResp getPostDetail(@PathVariable Long id) {
        return service.getDetail(id);
    }

}
