package com.basic.metablez.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ProductModel implements Parcelable, Serializable {
    public String productName, productWeight, productPrice, productImage;
    public int ptImg, productPriceDigit, productWeightDigit, multiplier;

    public ProductModel(String productName, String productWeight, int productWeightDigit, String productPrice, int productPriceDigit,
                        String productImage, int ptImg, int multiplier) {
        this.productName = productName;
        this.productWeight = productWeight;
        this.productWeightDigit = productWeightDigit;
        this.productPrice = productPrice;
        this.productPriceDigit = productPriceDigit;
        this.productImage = productImage;
        this.ptImg = ptImg;
        this.multiplier = multiplier;
    }

    protected ProductModel(Parcel in) {
        productName = in.readString();
        productWeight = in.readString();
        productWeightDigit = in.readInt();
        productPrice = in.readString();
        productPriceDigit = in.readInt();
        productImage = in.readString();
        ptImg = in.readInt();
        multiplier = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(productWeight);
        dest.writeInt(productWeightDigit);
        dest.writeString(productPrice);
        dest.writeInt(productPriceDigit);
        dest.writeString(productImage);
        dest.writeInt(ptImg);
        dest.writeInt(multiplier);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
}
