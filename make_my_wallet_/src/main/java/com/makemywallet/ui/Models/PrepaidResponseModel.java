package com.makemywallet.ui.Models;

public class PrepaidResponseModel {
    private int id;
    private String providerName;
    private String logo;
    private int statusId;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public PrepaidResponseModel() {
    }
    public PrepaidResponseModel(int id, String providerName) {
        this.id = id;
        this.providerName = providerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
