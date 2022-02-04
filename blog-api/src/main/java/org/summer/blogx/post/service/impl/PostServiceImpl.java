package org.summer.blogx.post.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.blogx.infrastructure.entity.Category;
import org.summer.blogx.infrastructure.entity.Post;
import org.summer.blogx.infrastructure.entity.PostCategory;
import org.summer.blogx.infrastructure.mapper.CategoryMapper;
import org.summer.blogx.infrastructure.mapper.PostCategoryMapper;
import org.summer.blogx.infrastructure.mapper.PostMapper;
import org.summer.blogx.infrastructure.pagination.PageReq;
import org.summer.blogx.infrastructure.rest.ErrorCode;
import org.summer.blogx.post.dto.PostDetailResp;
import org.summer.blogx.post.dto.PostListResp;
import org.summer.blogx.post.dto.PostReq;
import org.summer.blogx.post.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper mapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PostCategoryMapper postCategoryMapper;

    @Override
    public IPage<PostListResp> getPage(PageReq req) {
        return mapper.selectPage(req.toPage(), null).convert(p -> {
            PostListResp resp = new PostListResp();
            BeanUtils.copyProperties(p, resp);
            return  resp;
        });
    }

    @Transactional
    @Override
    public Long addPost(PostReq req) {
        //先插入新分类
        List<Category> existCategories = categoryMapper.selectList(Wrappers.lambdaQuery(Category.class)
                .in(Category::getName, req.getCategories()));
        List<String> existNames = existCategories.stream().map(Category::getName).collect(Collectors.toList());
        List<String> newCategories = req.getCategories().stream()
                .filter(c -> !existNames.contains(c)).collect(Collectors.toList());
        List<Long> categoryIds = existCategories.stream().map(Category::getId).collect(Collectors.toList());
        for (String category : newCategories) {
            Category c = new Category();
            c.setName(category);
            categoryMapper.insert(c);
            categoryIds.add(c.getId());
        }
        //插入post
        Post post = new Post();
        BeanUtils.copyProperties(req, post);
        mapper.insert(post);
        //管理post和category
        for (Long categoryId : categoryIds) {
            PostCategory pc = new PostCategory();
            pc.setPostId(post.getId());
            pc.setCategoryId(categoryId);
            postCategoryMapper.insert(pc);
        }
        log.info("post {} created", post.getId());
        return post.getId();
    }

    @Override
    public PostDetailResp getDetail(Long id) {
        Post post = mapper.selectById(id);
        if (post == null) {
            throw ErrorCode.PARAM_ERROR.newException(HttpStatus.NOT_FOUND, "post not found");
        }
        PostDetailResp resp = new PostDetailResp();
        BeanUtils.copyProperties(post, resp);
        List<PostCategory> postCategories = postCategoryMapper
                .selectList(Wrappers.lambdaQuery(PostCategory.class)
                .eq(PostCategory::getPostId, id));
        List<Category> categories = categoryMapper.selectList(Wrappers.lambdaQuery(Category.class).in(Category::getId,
                postCategories.stream().map(PostCategory::getCategoryId).collect(Collectors.toList())));
        resp.setCategories(categories.stream().map(c -> {
            PostDetailResp.CategoryResp cr = new PostDetailResp.CategoryResp();
            BeanUtils.copyProperties(c, cr);
            return cr;
        }).collect(Collectors.toList()));
        return resp;
    }

}
