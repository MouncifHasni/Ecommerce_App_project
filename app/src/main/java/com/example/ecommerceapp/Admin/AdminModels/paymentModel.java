package com.example.ecommerceapp.Admin.AdminModels;

import android.os.Parcel;
import android.os.Parcelable;

public class paymentModel implements Parcelable {
    String paypal;
    String mastercard;
    String visa;

    public paymentModel(String paypal, String mastercard,String visa) {
        this.paypal = paypal;
        this.mastercard = mastercard;
        this.visa = visa;
    }

    protected paymentModel(Parcel in) {
        paypal = in.readString();
        mastercard = in.readString();
        visa = in.readString();
    }

    public static final Creator<paymentModel> CREATOR = new Creator<paymentModel>() {
        @Override
        public paymentModel createFromParcel(Parcel in) {
            return new paymentModel(in);
        }

        @Override
        public paymentModel[] newArray(int size) {
            return new paymentModel[size];
        }
    };

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public String getMastercard() {
        return mastercard;
    }

    public void setMastercard(String mastercard) {
        this.mastercard = mastercard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(paypal);
        dest.writeString(mastercard);
        dest.writeString(visa);
    }
}
