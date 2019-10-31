package com.samlifttruck.activity.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.samlifttruck.R;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utility {
    private static final String TAG = "Utility";
    public static final String IS_LOGIN = "isLogin";
    public static final String LOGIN_USERNAME = "LoginUserName";
    public static final String LOGIN_WORKGROUP_ID = "LoginWorkgroupID";
    public static final String LOGIN_USER_ID = "LoginUserID";
    public static final String pw = "x4fg54-D9ib";
    public static final int NOT_FOUND_CODE = -404;


    public static iOSDialogBuilder newIOSdialog(Context context) {

        iOSDialogBuilder ios = new iOSDialogBuilder(context);
        Typeface font = ResourcesCompat.getFont(context, R.font.iran_sans_web);
        ios.setBoldPositiveLabel(true)
                .setCancelable(false)
                .setFont(font);
        return ios;
    }


    public static void closeKeyPad(@NonNull AppCompatActivity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "closeKeyPad: " + e.getLocalizedMessage());
        }
    }

    public static void showKeyPad(@NonNull AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static SharedPreferences returnPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        return pref;
    }


}
