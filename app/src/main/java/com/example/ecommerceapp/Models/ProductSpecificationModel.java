package com.example.ecommerceapp.Models;

public class ProductSpecificationModel {
    String specificationTitle,specificationValue;

    public ProductSpecificationModel(String specificationTitle, String specificationValue) {
        this.specificationTitle = specificationTitle;
        this.specificationValue = specificationValue;
    }

    public String getSpecificationTitle() {
        return specificationTitle;
    }

    public void setSpecificationTitle(String specificationTitle) {
        this.specificationTitle = specificationTitle;
    }

    public String getSpecificationValue() {
        return specificationValue;
    }

    public void setSpecificationValue(String specificationValue) {
        this.specificationValue = specificationValue;
    }
}
