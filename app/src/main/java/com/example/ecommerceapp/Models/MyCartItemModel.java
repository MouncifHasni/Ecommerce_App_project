package com.example.ecommerceapp.Models;

public class MyCartItemModel {
    private int productImage;
    private String title;
    private int price,lastPrice;
    private String productQuantity;

    public MyCartItemModel(int productImage, String title, int price, int lastPrice, String productQuantity) {
        this.productImage = productImage;
        this.title = title;
        this.price = price;
        this.lastPrice = lastPrice;
        this.productQuantity = productQuantity;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(int lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
