package org.summer.blogx.post.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostReq {
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String overview;
    @NotNull
    @NotBlank
    private String content;
    //文章所属分类
    @NotNull
    @Size(min = 1, max = 5)
    private List<@NotNull @NotBlank String> categories;
}
