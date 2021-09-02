package com.makemywallet.ui.Models;

public class PrepaidPlanModel {
    private String id;
    private String operatorId;
    private String circleId;
    private String rechargeAmount;
    private String rechargeTalktime;
    private String rechargeValidity;
    private String rechargeShortDesc;
    private String rechargeLongDesc;
    private String rechargeType;
    private String updatedAt;

    public PrepaidPlanModel(String id, String operatorId, String circleId, String rechargeAmount, String rechargeValidity, String rechargeShortDesc, String rechargeLongDesc, String rechargeType, String updatedAt) {
        this.id = id;
        this.operatorId = operatorId;
        this.circleId = circleId;
        this.rechargeAmount = rechargeAmount;
        this.rechargeValidity = rechargeValidity;
        this.rechargeShortDesc = rechargeShortDesc;
        this.rechargeLongDesc = rechargeLongDesc;
        this.rechargeType = rechargeType;
        this.updatedAt = updatedAt;
    }

    public PrepaidPlanModel(String id, String operatorId, String circleId, String rechargeAmount, String rechargeTalktime, String rechargeValidity, String rechargeShortDesc, String rechargeLongDesc, String rechargeType, String updatedAt) {
        this.id = id;
        this.operatorId = operatorId;
        this.circleId = circleId;
        this.rechargeAmount = rechargeAmount;
        this.rechargeTalktime = rechargeTalktime;
        this.rechargeValidity = rechargeValidity;
        this.rechargeShortDesc = rechargeShortDesc;
        this.rechargeLongDesc = rechargeLongDesc;
        this.rechargeType = rechargeType;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getCircleId() {
        return circleId;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public String getRechargeTalktime() {
        return rechargeTalktime;
    }

    public String getRechargeValidity() {
        return rechargeValidity;
    }

    public String getRechargeShortDesc() {
        return rechargeShortDesc;
    }

    public String getRechargeLongDesc() {
        return rechargeLongDesc;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
