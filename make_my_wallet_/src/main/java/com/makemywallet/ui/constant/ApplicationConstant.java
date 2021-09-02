package com.makemywallet.ui.constant;

public enum ApplicationConstant {
    INSTANCE;
    // public String BASE_URL="https://partner.makemywallet.co.in/";
    public String BASE_URL = "https://makemywallet.co.in/";
    public String PREPAID_RECHARGE = BASE_URL + "app/provider?service_id=";
    public String GET_RECHARGE = BASE_URL + "app/recharge";
    public String GET_ADD_SENDER_CHECK = BASE_URL + "app/m2/sender-check";
    public String GET_ADD_SENDER = BASE_URL + "app/m2/sender-add";
    public String GET_SEND_OTP = BASE_URL + "app/m2/sender-confirm";
    public String GET_IFSC = BASE_URL + "app/m2/get-ifsc";
    public String GET_BENE_LIST = BASE_URL + "app/m2/bene-list";
    public String GET_BANK_LIST = BASE_URL + "app/m2/bank-list";
    public String GET_DELETE_BENE = BASE_URL + "app/m2/delete-bene";
    public String GET_TRANSACTION = BASE_URL + "app/m2/transaction";
    public String GET_XPRESS_DMR_BENE_LIST = BASE_URL + "app/payout/bene-list";
    public String GET_XPRESS_DMR_ADD_BENE = BASE_URL + "app/payout/add-bene";
    public String GET_XPRESS_DMR_DELETE_BENE = BASE_URL + "app/payout/delete-bene";
    public String GET_XPRESS_DMR_BANK_LIST = BASE_URL + "app/m2/bank-list";
    public String GET_XPRESS_DMR_IFSC = BASE_URL + "app/m2/get-ifsc";
    public String GET_XPRESS_DMR_TRANSFER = BASE_URL + "app/xpress/transfer";
    public String GET_ACCOUNT_VERIFY = BASE_URL + "app/m2/account-verify";
    public String GET_STORE_BENE = BASE_URL + "app/m2/store-bene";
    public String GET_FUND_REQUEST = BASE_URL + "app/fund-request";
    public String GET_BANK_METHOD = BASE_URL + "app/bank-and-method";
    public String GET_CHANGE_PASSWORD = BASE_URL + "app/change-password";
    public String GET_ACCOUNT_STATEMENT = BASE_URL + "app/account-statement";
    public String GET_CHANGE_PIN = BASE_URL + "app/change-pin";
    public String GET_RECHARGE_HISTORY = BASE_URL + "app/recharge-history";
    public String GET_DMT_HISTORY = BASE_URL + "app/dmt-history";
    public String GET_PAYOUT_REPORT = BASE_URL + "app/payout-report";
    public String GET_TOPUP_VERIFY_MOBILE = BASE_URL + "app/wallet/topup/verify-mobile";
    public String GET_TOP_UP_TRANSACTION = BASE_URL + "app/wallet/topup/transaction";
    public String GET_PROFILE = BASE_URL + "app/user/profile";
    public String GET_PREPAID_PLANS = BASE_URL + "/app/prepaid-plan";
    public String GET_MAIN_WALLET = BASE_URL + "app/main-wallet-app";
    public String GET_REGION_API = BASE_URL + "app/GetRegion";
    public String GET_BILLER_BY_STATE = BASE_URL + "app/GetBillerByState?region=";
    public String GET_BILLER = BASE_URL + "app/GetBiller";
    public String GET_BILLER_INFO = BASE_URL + "app/BillerInfo?billerId=";
    public String GET_BILL_FETCH = BASE_URL + "app/BillFetch";
    public String GET_BILL_POSTPAID = BASE_URL + "app/GetBiller?category=MOBILE%20POSTPAID";

    public String GET_BILL_PAY = BASE_URL + "app/BillPay";
    public String GET_TIME_TRIM = BASE_URL + "app/timetrim";
    public String GET_CIRCLE = BASE_URL + "app/circle-list";

    public String GET_SLIDER_API = BASE_URL+ "app/image/slider";


}
