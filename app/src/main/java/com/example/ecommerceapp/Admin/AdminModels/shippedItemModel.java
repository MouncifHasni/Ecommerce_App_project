package com.example.ecommerceapp.Admin.AdminModels;

import android.os.Parcel;
import android.os.Parcelable;

public class shippedItemModel implements Parcelable {

    private String url,title,price;

    public shippedItemModel(String url, String title, String price) {
        this.url = url;
        this.title = title;
        this.price = price;
    }

    protected shippedItemModel(Parcel in) {
        url = in.readString();
        title = in.readString();
        price = in.readString();
    }

    public static final Creator<shippedItemModel> CREATOR = new Creator<shippedItemModel>() {
        @Override
        public shippedItemModel createFromParcel(Parcel in) {
            return new shippedItemModel(in);
        }

        @Override
        public shippedItemModel[] newArray(int size) {
            return new shippedItemModel[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(price);
    }
}
