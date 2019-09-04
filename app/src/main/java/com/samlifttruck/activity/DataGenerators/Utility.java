package com.samlifttruck.activity.DataGenerators;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.samlifttruck.R;

public class Utility {

    public static iOSDialogBuilder newIOSdialog(Context context) {

        iOSDialogBuilder ios = new iOSDialogBuilder(context);
        Typeface font = ResourcesCompat.getFont(context, R.font.iran_sans_web);
        ios.setBoldPositiveLabel(true)
                .setCancelable(false)
                .setFont(font);
        return ios;
    }

    public static class SOAP {
        public static final String URL = "http://192.168.1.10:8090/samWebService.asmx";
        public static final String NAMESPACE = "http://tempuri.org/";

        public static final String METHOD_GET_PRODUCT = "GetProductByTechNo";
        public static final String METHOD_EXECUTE_MIDTERM_COUNT = "ExecuteCycleCount";
        public static final String METHOD_GET_DRAFT_LIST = "GetListHavale";
        public static final String METHOD_GET_PERM_LIST = "GetListMojavez";
        public static final String METHOD_GET_RECEIPT_LIST = "GetListResid";
        public static final String METHOD_INSERT_PRODUCT = "InsertProduct";
        public static final String METHOD_LOGIN_CHECK = "LoginCheck";
        public static final String METHOD_UPDATE_MIDTERM_COUNT = "UpdateCycleCount";
        public static final String METHOD_UPDATE_SHELF = "UpdateProductShelf";

        public static final String SOAP_ACTION_GET_PRODUCT = NAMESPACE + METHOD_GET_PRODUCT;
        public static final String SOAP_ACTION_EXECUTE_MIDTERM_COUNT = NAMESPACE + METHOD_EXECUTE_MIDTERM_COUNT;
        public static final String SOAP_ACTION_GET_DRAFT_LIST = NAMESPACE + METHOD_GET_DRAFT_LIST;
        public static final String SOAP_ACTION_GET_PERM_LIST = NAMESPACE + METHOD_GET_PERM_LIST;
        public static final String SOAP_ACTION_GET_RECEIPT_LIST = NAMESPACE + METHOD_GET_RECEIPT_LIST;
        public static final String SOAP_ACTION_INSERT_PRODUCT = NAMESPACE + METHOD_INSERT_PRODUCT;
        public static final String SOAP_ACTION_LOGIN_CHECK = NAMESPACE + METHOD_LOGIN_CHECK;
        public static final String SOAP_ACTION_UPDATE_MIDTERM_COUNT = NAMESPACE + METHOD_UPDATE_MIDTERM_COUNT;
        public static final String SOAP_ACTION_UPDATE_SHELF = NAMESPACE + METHOD_UPDATE_SHELF;

        public static final int TIMEOUT = 15000;
    }
}
