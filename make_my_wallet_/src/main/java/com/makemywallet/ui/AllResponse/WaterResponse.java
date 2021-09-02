
package com.makemywallet.ui.AllResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaterResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

}
