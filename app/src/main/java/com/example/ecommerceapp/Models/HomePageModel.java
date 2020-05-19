package com.example.ecommerceapp.Models;

import android.widget.TextView;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLiDER = 0;
    public static final int Horizontal_scroll = 1;
    public static final int Grid_Layout = 2;
    private int type;
    //Banner
    private List<sliderModel> sliderModelList;
    //horizontal product
    private List<Horizontal_Product_Model> horizontalProductModelList;
    private String Title;
    //Grid Layout



    public HomePageModel(int type, List<sliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    //Banner
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<sliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<sliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    ////Horizontal scroll && Grid

    public HomePageModel(int type, List<Horizontal_Product_Model> horizontalProductModelList, String title) {
        this.type = type;
        this.horizontalProductModelList = horizontalProductModelList;
        this.Title = title;
    }

    public List<Horizontal_Product_Model> getHorizontalProductModelList() {
        return horizontalProductModelList;
    }

    public void setHorizontalProductModelList(List<Horizontal_Product_Model> horizontalProductModelList) {
        this.horizontalProductModelList = horizontalProductModelList;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
