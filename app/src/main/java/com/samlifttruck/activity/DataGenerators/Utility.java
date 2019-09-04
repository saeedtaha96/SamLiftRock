package com.samlifttruck.activity.DataGenerators;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;

import androidx.core.content.res.ResourcesCompat;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.samlifttruck.R;
import com.samlifttruck.activity.LoginActivity;

public class Utility {

    public static iOSDialogBuilder newIOSdialog(Context context) {

        iOSDialogBuilder ios = new iOSDialogBuilder(context);
        Typeface font = ResourcesCompat.getFont(context, R.font.iran_sans_web);
        ios.setBoldPositiveLabel(true)
                .setCancelable(false)
                .setFont(font);
        return ios;
    }
}
