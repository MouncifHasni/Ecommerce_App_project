package com.example.ecommerceapp.Admin.AdminModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class adminOrderModel implements Parcelable {

    @SerializedName("_id")
    private String id;

    private String totalitems;
    private String price;
    private String username;
    private String email;
    private String adress;
    private String phone;
    private String deliverystatus;
    private paymentModel payments;
    private List<shippedItemModel> shippeditems;

    public adminOrderModel(String id, String totalitems, String price, String username,String email, String adress, String phone, String deliverystatus, paymentModel payments, List<shippedItemModel> shippeditems) {
        this.id = id;
        this.totalitems = totalitems;
        this.price = price;
        this.username = username;
        this.email = email;
        this.adress = adress;
        this.phone = phone;
        this.deliverystatus = deliverystatus;
        this.payments = payments;
        this.shippeditems = shippeditems;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected adminOrderModel(Parcel in) {
        id = in.readString();
        totalitems = in.readString();
        price = in.readString();
        username = in.readString();
        adress = in.readString();
        phone = in.readString();
        deliverystatus = in.readString();
    }

    public static final Creator<adminOrderModel> CREATOR = new Creator<adminOrderModel>() {
        @Override
        public adminOrderModel createFromParcel(Parcel in) {
            return new adminOrderModel(in);
        }

        @Override
        public adminOrderModel[] newArray(int size) {
            return new adminOrderModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalitems() {
        return totalitems;
    }

    public void setTotalitems(String totalitems) {
        this.totalitems = totalitems;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public paymentModel getPayments() {
        return payments;
    }

    public void setPayments(paymentModel payments) {
        this.payments = payments;
    }

    public List<shippedItemModel> getShippeditems() {
        return shippeditems;
    }

    public void setShippeditems(List<shippedItemModel> shippeditems) {
        this.shippeditems = shippeditems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(totalitems);
        dest.writeString(price);
        dest.writeString(username);
        dest.writeString(adress);
        dest.writeString(phone);
        dest.writeString(deliverystatus);
    }
}
