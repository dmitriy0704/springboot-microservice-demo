package dev.folomkin.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Autowired
    private EurekaInstanceConfigBean eurekaInstanceConfig;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        // Устанавливаем реально выделенный порт в конфиг Eureka
        eurekaInstanceConfig.setNonSecurePort(event.getWebServer().getPort());
    }
}