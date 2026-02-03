package dev.folomkin.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users/user-payments")
public class UserPaymentsController {

    private int port;

    @EventListener
    public String onWebServerReady(WebServerInitializedEvent event) {
         port = event.getWebServer().getPort();
        System.out.println("Реальный порт: " + port);
        return "hello from order-service on port " + port;
    }


    @GetMapping("/getlist")
    public String getUserPayments(){
        log.info("Handled by instance on port {}", port);
        return "hello from order-service on port " + port;
    }
}
