package com.example.ecommerceapp.Models;

public class MyFavModel {

    private int productImage;
    private String title,price,lastprice,rating,totalRatings;

    public MyFavModel(int productImage, String title, String price, String lastprice, String rating, String totalRatings) {
        this.productImage = productImage;
        this.title = title;
        this.price = price;
        this.lastprice = lastprice;
        this.rating = rating;
        this.totalRatings = totalRatings;
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

    public String getLastprice() {
        return lastprice;
    }

    public void setLastprice(String lastprice) {
        this.lastprice = lastprice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(String totalRatings) {
        this.totalRatings = totalRatings;
    }
}
