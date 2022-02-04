package org.summer.blogx.infrastructure.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * post category 关系表
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class PostCategory extends BaseEntity {
    private Long postId;
    private Long categoryId;
}
