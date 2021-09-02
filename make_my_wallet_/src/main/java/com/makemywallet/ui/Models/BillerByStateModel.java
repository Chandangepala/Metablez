package com.makemywallet.ui.Models;

public class BillerByStateModel {
    private String billerId;
    private String billerName;

    public BillerByStateModel(String billerId, String billerName) {
        this.billerId = billerId;
        this.billerName = billerName;
    }

    public String getBillerId() {
        return billerId;
    }

    public String getBillerName() {
        return billerName;
    }
}
