package org.summer.blogx.infrastructure.pagination;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PageReq {
    @Min(1)
    private Integer page = 1;
    @Min(1)
    private Integer rows = 10;

    public <T> Page<T> toPage() {
        return new Page<>(page, rows);
    }

}
