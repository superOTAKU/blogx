package org.summer.blogx.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostListResp {
    private Long id;
    private String title;
    private String overview;
    private LocalDateTime createTime;
}
