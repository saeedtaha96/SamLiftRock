package com.samlifttruck.activity.DataGenerators;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.samlifttruck.R;

public class Utility {

    public static final String IS_LOGIN = "isLogin";
    public static final String LOGIN_USERNAME = "LoginUserName";
    public static final String LOGIN_WORKGROUP_ID = "LoginWorkgroupID";

    public static iOSDialogBuilder newIOSdialog(Context context) {

        iOSDialogBuilder ios = new iOSDialogBuilder(context);
        Typeface font = ResourcesCompat.getFont(context, R.font.iran_sans_web);
        ios.setBoldPositiveLabel(true)
                .setCancelable(false)
                .setFont(font);
        return ios;
    }


}
