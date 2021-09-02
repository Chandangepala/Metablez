package com.basic.metablez.models;

public class ProductAlsoModel {
    public String pdtName, pdtPrice, pdtWt, pdtBtnCenterTxt ,pdtImgUrl;
    public int pdtImg, pdtPriceDigit, pdtWtDigit, multiplier;

    public ProductAlsoModel(String pdtName, String pdtWt,  int pdtWtDigit, String pdtPrice, int pdtPriceDigit,  String pdtBtnCenterTxt, String pdtImgUrl, int pdtImg, int multiplier) {
        this.pdtName = pdtName;
        this.pdtWt = pdtWt;
        this.pdtWtDigit = pdtWtDigit;
        this.pdtPrice = pdtPrice;
        this.pdtPriceDigit = pdtPriceDigit;
        this.pdtBtnCenterTxt = pdtBtnCenterTxt;
        this.pdtImgUrl = pdtImgUrl;
        this.pdtImg = pdtImg;
        this.multiplier = multiplier;
    }
}
