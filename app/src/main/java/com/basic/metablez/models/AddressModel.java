package com.basic.metablez.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressModel implements Parcelable {
    public String fullName, mobileNo, houseNo, street, landmark, city, pincode;

    public AddressModel(String fullName, String mobileNo, String houseNo, String street, String landmark, String city, String pincode) {
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.houseNo = houseNo;
        this.street = street;
        this.landmark = landmark;
        this.city = city;
        this.pincode = pincode;
    }

    protected AddressModel(Parcel in) {
        fullName = in.readString();
        mobileNo = in.readString();
        houseNo = in.readString();
        street = in.readString();
        landmark = in.readString();
        city = in.readString();
        pincode = in.readString();
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(mobileNo);
        dest.writeString(houseNo);
        dest.writeString(street);
        dest.writeString(landmark);
        dest.writeString(city);
        dest.writeString(pincode);
    }
}
