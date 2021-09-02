
package com.makemywallet.ui.AllResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("main_balance")
    @Expose
    private String mainBalance;
    @SerializedName("aeps_balance")
    @Expose
    private String aepsBalance;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("recharge")
    @Expose
    private Integer recharge;
    @SerializedName("money")
    @Expose
    private Integer money;
    @SerializedName("payout")
    @Expose
    private Integer payout;
    @SerializedName("pancard")
    @Expose
    private Integer pancard;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainBalance() {
        return mainBalance;
    }

    public void setMainBalance(String mainBalance) {
        this.mainBalance = mainBalance;
    }

    public String getAepsBalance() {
        return aepsBalance;
    }

    public void setAepsBalance(String aepsBalance) {
        this.aepsBalance = aepsBalance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRecharge() {
        return recharge;
    }

    public void setRecharge(Integer recharge) {
        this.recharge = recharge;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getPayout() {
        return payout;
    }

    public void setPayout(Integer payout) {
        this.payout = payout;
    }

    public Integer getPancard() {
        return pancard;
    }

    public void setPancard(Integer pancard) {
        this.pancard = pancard;
    }

}
