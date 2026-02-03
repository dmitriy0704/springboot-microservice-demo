package dev.folomkin.userservice.controller;

import dev.folomkin.userservice.service.UserOrdersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/user-orders")
public class UserOrdersController {

    private final UserOrdersService userOrdersService;

    public UserOrdersController(UserOrdersService userOrdersService) {
        this.userOrdersService = userOrdersService;
    }


    @GetMapping("/hello")
    public String getUser(){
        return userOrdersService.callOrderService();
    }
}
