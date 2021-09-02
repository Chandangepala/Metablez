package com.makemywallet.ui.Models;

public class BankDetailDemoModel {
    private String id;
    private String holder_name;
    private String bankName;
    private String bankIfsc;
    private String bankAccount;
    private String bankBranch;

    public BankDetailDemoModel(String id, String holder_name, String bankName, String bankIfsc, String bankAccount, String bankBranch) {
        this.id = id;
        this.holder_name = holder_name;
        this.bankName = bankName;
        this.bankIfsc = bankIfsc;
        this.bankAccount = bankAccount;
        this.bankBranch = bankBranch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
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

}
