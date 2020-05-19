package com.example.ecommerceapp.Admin.AdminModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class adminCatModel {

    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("items")
    private List<adminItemModel> items;

    public adminCatModel(String title, String url,List<adminItemModel> items) {
        this.title = title;
        this.url = url;
        this.items = items;
    }

    public List<adminItemModel> getItems() {
        return items;
    }

    public void setItems(List<adminItemModel> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
