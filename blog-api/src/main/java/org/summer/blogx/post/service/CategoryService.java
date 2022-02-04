package org.summer.blogx.post.service;

import org.summer.blogx.post.dto.CategoryResp;

import java.util.List;

public interface CategoryService {

    List<CategoryResp> getCategories();

}
