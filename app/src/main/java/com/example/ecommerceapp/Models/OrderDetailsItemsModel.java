package com.example.ecommerceapp.Models;

import android.widget.ImageView;

public class OrderDetailsItemsModel {
    private String title,price;
    private int productImage;

    public OrderDetailsItemsModel(int productImage, String title, String price) {
        this.title = title;
        this.price = price;
        this.productImage = productImage;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
