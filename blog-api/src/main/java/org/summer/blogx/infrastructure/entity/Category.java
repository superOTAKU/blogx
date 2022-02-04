package org.summer.blogx.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 文章分类，作用是归档
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Category extends BaseEntity{
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
}
