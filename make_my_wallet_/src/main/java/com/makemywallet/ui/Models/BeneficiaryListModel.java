package com.makemywallet.ui.Models;

public class BeneficiaryListModel {
    private String beneficiaryId;
    private String status;
    private String recipientName;
    private String bank;
    private String ifsc;
    private String account;

    public BeneficiaryListModel() {
    }

    public BeneficiaryListModel(String beneficiaryId, String status, String recipientName, String bank, String ifsc, String account) {
        this.beneficiaryId = beneficiaryId;
        this.status = status;
        this.recipientName = recipientName;
        this.bank = bank;
        this.ifsc = ifsc;
        this.account = account;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
