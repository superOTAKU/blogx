package org.summer.blogx.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.summer.blogx.post.dto.CategoryResp;
import org.summer.blogx.post.service.CategoryService;

import java.util.List;

@RequestMapping("/api/category")
@PreAuthorize("permitAll()")
@Validated
@RestController
public class CategoryApiController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public List<CategoryResp> getCategories() {
        return service.getCategories();
    }

}
