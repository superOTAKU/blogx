package org.summer.blogx.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 文章
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class Post extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String overview;
    private String content;
    private Boolean deleted;
}
