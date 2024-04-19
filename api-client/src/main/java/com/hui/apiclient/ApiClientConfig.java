package com.hui.apiclient;/**
 * 作者:灰爪哇
 * 时间:2024-04-18
 */

import com.hui.apiclient.client.ApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author: Hui
 **/
@Data
@Configuration
@ConfigurationProperties("api.client")
@ComponentScan
public class ApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public ApiClient apiClient() {
        return new ApiClient(accessKey, secretKey);
    }

}
