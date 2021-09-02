package com.basic.metablez.modelResponse;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("success")
    public String success;
    @SerializedName("userId")
    public Number userId;
    @SerializedName("OTP")
    public Number OTP;

    public RegisterModel(String success, Number userId, Number OTP) {
        this.success = success;
        this.userId = userId;
        this.OTP = OTP;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Number getUserId() {
        return userId;
    }

    public void setUserId(Number userId) {
        this.userId = userId;
    }

    public Number getOTP() {
        return OTP;
    }

    public void setOTP(Number OTP) {
        this.OTP = OTP;
    }
}
