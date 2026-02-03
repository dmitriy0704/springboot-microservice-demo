package dev.folomkin.userservice.service;


import dev.folomkin.userservice.http.OrderClient;
import org.springframework.stereotype.Service;

@Service
public class UserOrdersService {

    private final OrderClient orderClient;

    public UserOrdersService(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public String callOrderService(){
        return orderClient.hello();
    }
}
