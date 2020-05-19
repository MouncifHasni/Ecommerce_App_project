package com.example.ecommerceapp.Models;

public class MyOrdersModel {
    private int productImage;
    private String deliveryStatut,title;

    public MyOrdersModel(int productImage, String deliveryStatut,String title) {
        this.productImage = productImage;
        this.deliveryStatut = deliveryStatut;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getDeliveryStatut() {
        return deliveryStatut;
    }

    public void setDeliveryStatut(String deliveryStatut) {
        this.deliveryStatut = deliveryStatut;
    }
}
