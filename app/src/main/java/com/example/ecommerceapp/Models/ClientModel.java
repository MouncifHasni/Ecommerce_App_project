package com.example.ecommerceapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientModel implements Parcelable {

    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("avatar")
    private String avatar;
    private List<AdressModel> adress;
    private List<adminItemModel> favourites;
    private List<adminItemModel> wishlist;
    private List<adminItemModel> orders;

    public ClientModel(String email, String username, String password, String avatar,List<AdressModel> adress,List<adminItemModel> favourites, List<adminItemModel> wishlist, List<adminItemModel> orders) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.adress = adress;
        this.favourites = favourites;
        this.wishlist = wishlist;
        this.orders = orders;
    }


    protected ClientModel(Parcel in) {
        email = in.readString();
        username = in.readString();
        password = in.readString();
        avatar = in.readString();
    }

    public static final Creator<ClientModel> CREATOR = new Creator<ClientModel>() {
        @Override
        public ClientModel createFromParcel(Parcel in) {
            return new ClientModel(in);
        }

        @Override
        public ClientModel[] newArray(int size) {
            return new ClientModel[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<AdressModel> getAdress() {
        return adress;
    }

    public void setAdress(List<AdressModel> adress) {
        this.adress = adress;
    }

    public List<adminItemModel> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<adminItemModel> favourites) {
        this.favourites = favourites;
    }

    public List<adminItemModel> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<adminItemModel> wishlist) {
        this.wishlist = wishlist;
    }

    public List<adminItemModel> getOrders() {
        return orders;
    }

    public void setOrders(List<adminItemModel> orders) {
        this.orders = orders;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(avatar);
    }
}
