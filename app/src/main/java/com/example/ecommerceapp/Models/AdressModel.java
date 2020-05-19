package com.example.ecommerceapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class AdressModel implements Parcelable {

    private String _id;
    private String receiverusername;
    private String street_adress;
    private String country;
    private String city;
    private String province;
    private String phone;
    private String zipcode;
    private String adress_type;

    public AdressModel(String _id,String receiverusername, String street_adress, String country, String city, String province, String phone, String zipcode, String adress_type) {
        this.receiverusername = receiverusername;
        this.street_adress = street_adress;
        this.country = country;
        this.city = city;
        this.province = province;
        this.phone = phone;
        this._id = _id;
        this.zipcode = zipcode;
        this.adress_type = adress_type;
    }

    protected AdressModel(Parcel in) {
        _id = in.readString();
        receiverusername = in.readString();
        street_adress = in.readString();
        country = in.readString();
        city = in.readString();
        province = in.readString();
        phone = in.readString();
        zipcode = in.readString();
        adress_type = in.readString();
    }

    public static final Creator<AdressModel> CREATOR = new Creator<AdressModel>() {
        @Override
        public AdressModel createFromParcel(Parcel in) {
            return new AdressModel(in);
        }

        @Override
        public AdressModel[] newArray(int size) {
            return new AdressModel[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStreet_adress() {
        return street_adress;
    }

    public void setStreet_adress(String street_adress) {
        this.street_adress = street_adress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAdress_type() {
        return adress_type;
    }

    public void setAdress_type(String adress_type) {
        this.adress_type = adress_type;
    }

    public String getReceiverusername() {
        return receiverusername;
    }

    public void setReceiverusername(String receiverusername) {
        this.receiverusername = receiverusername;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(receiverusername);
        dest.writeString(street_adress);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(phone);
        dest.writeString(zipcode);
        dest.writeString(adress_type);
    }
}
