package com.makemywallet.ui.Models;

public class BankDetailModel {
    private String id;
    private String bankName;
    private String holderName;
    private String bankIfsc;
    private String bankBranch;
    private String bankAccount;
    private String created_at;
    private String methodName;

    public String getMethodName() {
        return methodName;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public BankDetailModel() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankIfsc() {
        return bankIfsc;
    }
    public void setBankIfsc(String bankIfsc) {
        this.bankIfsc = bankIfsc;
    }
    public String getBankBranch() {
        return bankBranch;
    }
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
    public String getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
