package dev.folomkin.userservice.model.dto;

public class UpdateUserProfileDto {

    private String name;
    private String phone;
    private String city;
    private String address;

    public UpdateUserProfileDto() {
    }

    public UpdateUserProfileDto(String name, String phone, String city, String address) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
