package org.summer.blogx.infrastructure.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.summer.blogx.infrastructure.bootstrap.config.BootstrapConfig;
import org.summer.blogx.infrastructure.entity.User;
import org.summer.blogx.infrastructure.mapper.UserMapper;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BootstrapConfig config;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<User> users = mapper.selectList(null);
        if (!users.isEmpty()) {
            return;
        }
        String passwordSalt = randomSalt();
        String encodedPassword = passwordEncoder.encode(config.getPassword() + passwordSalt);
        User user = new User();
        user.setUsername(config.getUsername());
        user.setPassword(encodedPassword);
        user.setPasswordHash(passwordSalt);
        mapper.insert(user);
        log.info("bootstrap user, id:{}, username: {}", user.getId(), user.getUsername());
    }

    private String randomSalt() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            char charStart = ThreadLocalRandom.current().nextInt(0, 2) == 0 ? 'a' : 'A';
            sb.append((char)(charStart + ThreadLocalRandom.current().nextInt(0, 26)));
        }
        return sb.toString();
    }
}
