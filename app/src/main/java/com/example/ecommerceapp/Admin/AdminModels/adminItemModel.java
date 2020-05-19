package com.example.ecommerceapp.Admin.AdminModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class adminItemModel implements Parcelable {
    String _id;
    String title;
    String url;
    String desc;
    String price;
    String lastprice;
    String rating;
    String totalrating;
    String condition;
    String quantity;
    String brand;
    paymentModel payments;
    String returntype;
    String shippingcost;
    String deliverytime;

    public adminItemModel(String _id,String title,String url, String desc, String price, String lastprice, String rating, String totalrating, String condition, String quantity, String brand, paymentModel payments, String returntype, String shippingcost, String deliverytime) {
        this._id = _id;
        this.title = title;
        this.url = url;
        this.desc = desc;
        this.price = price;
        this.lastprice = lastprice;
        this.rating = rating;
        this.totalrating = totalrating;
        this.condition = condition;
        this.quantity = quantity;
        this.brand = brand;
        this.payments = payments;
        this.returntype = returntype;
        this.shippingcost = shippingcost;
        this.deliverytime = deliverytime;
    }

    protected adminItemModel(Parcel in) {
        _id = in.readString();
        title = in.readString();
        url = in.readString();
        desc = in.readString();
        price = in.readString();
        lastprice = in.readString();
        rating = in.readString();
        totalrating = in.readString();
        condition = in.readString();
        quantity = in.readString();
        brand = in.readString();
        payments = in.readParcelable(paymentModel.class.getClassLoader());
        returntype = in.readString();
        shippingcost = in.readString();
        deliverytime = in.readString();
    }

    public static final Creator<adminItemModel> CREATOR = new Creator<adminItemModel>() {
        @Override
        public adminItemModel createFromParcel(Parcel in) {
            return new adminItemModel(in);
        }

        @Override
        public adminItemModel[] newArray(int size) {
            return new adminItemModel[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getTotalrating() {
        return totalrating;
    }

    public void setTotalrating(String totalrating) {
        this.totalrating = totalrating;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public paymentModel getPayments() {
        return payments;
    }

    public void setPayments(paymentModel payments) {
        this.payments = payments;
    }

    public String getReturntype() {
        return returntype;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
    }

    public String getShippingcost() {
        return shippingcost;
    }

    public void setShippingcost(String shippingcost) {
        this.shippingcost = shippingcost;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(desc);
        dest.writeString(price);
        dest.writeString(lastprice);
        dest.writeString(rating);
        dest.writeString(totalrating);
        dest.writeString(condition);
        dest.writeString(quantity);
        dest.writeString(brand);
        dest.writeParcelable(payments, flags);
        dest.writeString(returntype);
        dest.writeString(shippingcost);
        dest.writeString(deliverytime);
    }
}
