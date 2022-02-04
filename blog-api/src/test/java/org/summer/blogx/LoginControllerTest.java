package org.summer.blogx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.summer.blogx.infrastructure.bootstrap.config.BootstrapConfig;
import org.summer.blogx.infrastructure.jackson.JsonHelper;
import org.summer.blogx.post.dto.PostReq;
import org.summer.blogx.security.config.SecurityConstants;
import org.summer.blogx.user.dto.AccessToken;
import org.summer.blogx.user.dto.LoginReq;
import org.summer.blogx.user.dto.RefreshTokenReq;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BootstrapConfig bootstrapConfig;

    @Autowired
    private JsonHelper jsonHelper;

    private AccessToken accessToken;

    @Order(1)
    @Test
    public void login() throws Exception {
        LoginReq req = new LoginReq();
        req.setUsername(bootstrapConfig.getUsername());
        req.setPassword(bootstrapConfig.getPassword());
        String responseBody = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(req))
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        AccessToken accessToken = jsonHelper.toObject(responseBody, AccessToken.class);
        this.accessToken = accessToken;
        log.info("login success, accessToken is {}", accessToken);
    }

    @Order(2)
    @Test
    public void refreshToken() throws Exception {
        RefreshTokenReq req = new RefreshTokenReq();
        req.setRefreshToken(accessToken.getRefreshToken());
        String responseBody = mvc.perform(post("/refreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(req))
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        AccessToken accessToken = jsonHelper.toObject(responseBody, AccessToken.class);
        this.accessToken = accessToken;
        log.info("refresh token success, accessToken is {}", accessToken);
    }

    @Order(3)
    @Test
    public void addPost() throws Exception {
        PostReq req = new PostReq();
        req.setTitle("Test Blog");
        req.setOverview("This is for test");
        req.setContent("This is test content");
        req.setCategories(List.of("Java", "JavaScript"));
        //先不用token，返回401
        MockHttpServletRequestBuilder requestBuilder = post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(req));
        mvc.perform(requestBuilder).andExpect(status().isUnauthorized());
        //使用token，期望正常插入记录
        mvc.perform(requestBuilder
                .header(SecurityConstants.AUTHENTICATION_HEADER, accessToken.getToken()))
                .andExpect(status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getContentAsString()));
    }

    @Order(4)
    @Test
    public void getPostPage() throws Exception {
        mvc.perform(get("/api/post?page=1&rows=10"))
                .andExpect(status().isOk())
                .andDo(mvcResult -> log.info("post page: {}", mvcResult.getResponse().getContentAsString()));
    }

}
