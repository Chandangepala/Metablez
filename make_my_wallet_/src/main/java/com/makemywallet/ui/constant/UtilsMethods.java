package com.makemywallet.ui.constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.makemywallet.MainActivity;
import com.makemywallet.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public enum UtilsMethods {

    INSTANCE;

    public void getProcessing(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Processing")
                .setContentText(message)
                .setCustomImage(R.drawable.processing)
                .show();
    }

    public void getFailed(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.attention_error_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_cancel_black_24dp)
                .show();
    }

    public void getSuccessful(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.successful_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_check_circle_green)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                      /* if(i==1){
                           ActivityActivityMessage activityActivityMessage =
                                   new ActivityActivityMessage("StateAlertEnable","");
                           GlobalBus.getBus().post(activityActivityMessage);
                           sweetAlertDialog.dismiss();
                        }
                        if(i==2){
                           ActivityActivityMessage activityActivityMessage =
                                   new ActivityActivityMessage("StateAlertDisable","");
                           GlobalBus.getBus().post(activityActivityMessage);
                           sweetAlertDialog.dismiss();
                        }
                        if(i==3){
                           ActivityActivityMessage activityActivityMessage =
                                   new ActivityActivityMessage("SpeedAlertEnable",from);
                           GlobalBus.getBus().post(activityActivityMessage);
                           sweetAlertDialog.dismiss();
                        }
                        if(i==4){
                           ActivityActivityMessage activityActivityMessage =
                                   new ActivityActivityMessage("SpeedAlertDisable",from);
                           GlobalBus.getBus().post(activityActivityMessage);
                           sweetAlertDialog.dismiss();
                        }
                        if(i==5){
                            ((DeviceInfoActivity) context).onBackPressed();
                           sweetAlertDialog.dismiss();
                        }
                        if(i==6){
                            ((AlermCreate) context).onBackPressed();
                            sweetAlertDialog.dismiss();
                        }
                        else {
                            sweetAlertDialog.dismiss();
                        }*/
                    }
                })
                .show();
    }

    public void getNetworkError(final Context context, String title, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setCustomImage(R.drawable.ic_connection_lost_24dp)
                .show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnectedOrConnecting()) {
            return true;
        }
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnectedOrConnecting()) {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /* For Toast*/
    public static void getShowToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

   /*
   public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    } */

    /*
     * retreiving the data from shared preferences
     */
    public static String getPreference(Context context, String key) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences("PREFS_MAKE_MY_WALLET", context.MODE_PRIVATE);
            return prefs.getString(key, "");
        }
        return "";
    }

    /*
     * setting data in shared preferences
     */
    public static void setPreference(Context context, String key, String value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences("PREFS_MAKE_MY_WALLET", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }
}
