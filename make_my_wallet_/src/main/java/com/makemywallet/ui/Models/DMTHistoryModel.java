package com.makemywallet.ui.Models;

public class DMTHistoryModel {
    private String orderId;
    private String date;
    private String number;
    private String amount;
    private String txnid;
    private String profit;
    private String surcharge;
    private String gst;
    private String tds;
    private String totalBalance;
    private String status;


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public void setSurcharge(String surcharge) {
        this.surcharge = surcharge;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DMTHistoryModel() {
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date;
    }

    public String getNumber() {
        return number;
    }

    public String getAmount() {
        return amount;
    }

    public String getTxnid() {
        return txnid;
    }

    public String getProfit() {
        return profit;
    }

    public String getSurcharge() {
        return surcharge;
    }

    public String getGst() {
        return gst;
    }

    public String getTds() {
        return tds;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public String getStatus() {
        return status;
    }
}
