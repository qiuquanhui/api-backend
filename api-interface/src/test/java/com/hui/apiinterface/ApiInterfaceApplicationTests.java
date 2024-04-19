package com.hui.apiinterface;

import com.hui.apiclient.client.ApiClient;
import com.hui.apiclient.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ApiInterfaceApplicationTests {

    @Resource
    private ApiClient apiClient;

    @Test
    void sdkTest() {
        apiClient.getNameByGet("hui");
    }

    @Test
    void clientTest() {
        apiClient.getNameByGet("hui");
        apiClient.getNameByPost("hui");
        User user = new User();
        user.setName("hui");
        apiClient.getUserNameByPost(user);
    }

}
