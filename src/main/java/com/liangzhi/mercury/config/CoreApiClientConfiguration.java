package com.liangzhi.mercury.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liangzhi.commons.api.client.CoreApiClient;

@Configuration
public class CoreApiClientConfiguration {
    
    
    @Value("${core.api.key}")
    private String apiKey;
    
    @Value("${core.api.secret}")
    private String apiSecret;
    
    @Value("${core.api.host}")
    private String baseUrl;
    
    @Value("${core.api.admin.host}")
    private String adminUrl;
    
    private Logger LOGGER = LoggerFactory.getLogger(CoreApiClientConfiguration.class);
    
    @Bean
    public CoreApiClient coreApiClient() {
        CoreApiClient client = new CoreApiClient(apiKey, apiSecret, baseUrl);
        return client;
    }

}
