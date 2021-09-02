package com.makemywallet.ui.Models;

public class PostpaidModel {
    private String billerId;
    private String billerName;

    public PostpaidModel() {
    }

    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }
}
