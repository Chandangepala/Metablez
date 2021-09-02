package com.basic.metablez.models;

public class ShopModel {
    public String shopImgUrl, shopName, shopAddress;
    public int shopRating, shopImg;

    public ShopModel(String shopImgUrl, String shopName, String shopAddress, int shopRating, int shopImg) {
        this.shopImgUrl = shopImgUrl;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopRating = shopRating;
        this.shopImg = shopImg;
    }

}
