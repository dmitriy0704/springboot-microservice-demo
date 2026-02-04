package dev.folomkin.orderservice.model.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Автогенерация UUID
    private UUID id;

    private String userid;

    private String name;

    private int amount;
    

    public Order() {
    }

    public Order(String userid, String name, int amount) {
        this.userid = userid;
        this.name = name;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
