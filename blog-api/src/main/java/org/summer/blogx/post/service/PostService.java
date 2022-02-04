package org.summer.blogx.post.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.summer.blogx.infrastructure.pagination.PageReq;
import org.summer.blogx.post.dto.PostDetailResp;
import org.summer.blogx.post.dto.PostListResp;
import org.summer.blogx.post.dto.PostReq;

public interface PostService {

    IPage<PostListResp> getPage(PageReq req);

    Long addPost(PostReq req);

    PostDetailResp getDetail(Long id);

}
