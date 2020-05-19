package com.example.ecommerceapp.Models;

public class CategoryModel {
    private int categoryImage;
    private String Title;

    public CategoryModel(int categoryImage, String title) {
        this.categoryImage = categoryImage;
        Title = title;
    }


    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
