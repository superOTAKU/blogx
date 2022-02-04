package org.summer.blogx.post.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.blogx.infrastructure.mapper.CategoryMapper;
import org.summer.blogx.post.dto.CategoryResp;
import org.summer.blogx.post.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper mapper;
    @Override
    public List<CategoryResp> getCategories() {
        return mapper.selectList(null).stream().map(c -> {
            CategoryResp resp = new CategoryResp();
            BeanUtils.copyProperties(c, resp);
            return resp;
        }).collect(Collectors.toList());
    }
}
