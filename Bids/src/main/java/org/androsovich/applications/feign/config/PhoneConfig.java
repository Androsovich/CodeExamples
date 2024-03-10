package org.androsovich.applications.feign.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import org.androsovich.applications.feign.decoder.FeignErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class PhoneConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Decoder decoder() {
        return new GsonDecoder();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor(@Value("${application.dadata.secret.key}") String secretKey,
                                                 @Value("${application.dadata.api.key}") String apiKey) {
        return requestTemplate -> {
            requestTemplate.header("X-Secret", secretKey);
            requestTemplate.header("Authorization", "Token " + apiKey);
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("charset", "utf-8");
        };
    }
}
