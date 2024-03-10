package org.androsovich.applications.feign.clients;

import org.androsovich.applications.entities.embeddeds.Phone;
import org.androsovich.applications.feign.config.PhoneConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(
        name = "phoneClient",
        url = "${spring.cloud.openfeign.client.config.phoneClient.url}",
        configuration = PhoneConfig.class)
public interface PhoneClient {

    @PostMapping
    Phone[] checkPhone(String phone);
}
