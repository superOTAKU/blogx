package org.summer.blogx.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 系统用户，由于是个人博客，所以只会有一个用户
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    //用户名
    private String username;
    //hash(rawPassword + passwordHash)
    private String password;
    //哈希盐值，增大密码破解难度
    private String passwordHash;
}
