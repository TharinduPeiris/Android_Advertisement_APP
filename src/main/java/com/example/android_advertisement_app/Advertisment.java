package com.example.android_advertisement_app;

public class Advertisment {


    private long id;
    private String name;
    private String price;
    private String description;
    private byte[] image;
    public String location;
    public String contactNo;
    private int user_id;


    public Advertisment() {
    }

    public Advertisment(String name) {
        this.name=name;
    }


    public Advertisment(String name, String price, String description, byte[] image, String contactNo, String location, int user_id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.location = location;
        this.contactNo = contactNo;
        this.user_id = user_id;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }


}