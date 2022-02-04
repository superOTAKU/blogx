package org.summer.blogx.infrastructure.bootstrap.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "bootstrap")
@Configuration
@Data
public class BootstrapConfig {
    private String username;
    private String password;
}
