package dev.folomkin.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/user-orders")
public class UserOrdersController {

    @GetMapping("/hellouser")
    public String getUser(){
        return "Get user hello";
    }
}
