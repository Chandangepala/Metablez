package com.basic.retailer_meatablez.models;

public class MenuProductModel {
    public String productName, productPriceTxt,productImgUrl;
    public int productImg, productPrice;

    public MenuProductModel(String productName, String productPriceTxt, String productImgUrl, int productImg, int productPrice) {
        this.productName = productName;
        this.productPriceTxt = productPriceTxt;
        this.productImgUrl = productImgUrl;
        this.productImg = productImg;
        this.productPrice = productPrice;
    }
}
