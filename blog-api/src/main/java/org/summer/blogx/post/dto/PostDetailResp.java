package org.summer.blogx.post.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDetailResp {
    private Long id;
    private String title;
    private String overview;
    private String content;
    private LocalDateTime createTime;
    private List<CategoryResp> categories;

    @Data
    public static class CategoryResp {
        private Long id;
        private String name;
    }

}
