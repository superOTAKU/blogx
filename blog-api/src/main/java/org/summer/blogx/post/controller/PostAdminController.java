package org.summer.blogx.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.summer.blogx.post.dto.PostReq;
import org.summer.blogx.post.service.PostService;

import javax.validation.Valid;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/post")
@Validated
@RestController
public class PostAdminController {

    @Autowired
    private PostService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long addPost(@RequestBody @Valid PostReq req) {
        return service.addPost(req);
    }

}
