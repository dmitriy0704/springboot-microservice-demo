package dev.folomkin.orderservice.model.entity;


public class Order {
    private long id;
    private String name;
    private int amount;

    public Order() {
    }

    public Order(long id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
