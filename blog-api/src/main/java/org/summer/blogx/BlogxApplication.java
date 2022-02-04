package org.summer.blogx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@MapperScan("org.summer.blogx.infrastructure.mapper")
@SpringBootApplication
public class BlogxApplication {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("UTC")));
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogxApplication.class, args);
    }

}
