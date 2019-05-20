package com.wu.shirotest.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class GithubPojo {

    @Value("${clientId}")
    private String clientId;
    @Value("${ClientSecret}")
    private String ClientSecret;

    public GithubPojo() {
    }

    public GithubPojo(String clientId, String clientSecret) {
        this.clientId = clientId;
        ClientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return ClientSecret;
    }

    public void setClientSecret(String clientSecret) {
        ClientSecret = clientSecret;
    }

    @Override
    public String toString() {
        return "GithubPojo{" +
                "clientId='" + clientId + '\'' +
                ", ClientSecret='" + ClientSecret + '\'' +
                '}';
    }
}
