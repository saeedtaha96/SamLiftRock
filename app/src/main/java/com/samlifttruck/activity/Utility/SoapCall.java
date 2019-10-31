package com.samlifttruck.activity.Utility;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class SoapCall extends AsyncTask<PropertyInfo, Object, ArrayList<JSONObject>> {
    private static final String TAG = "SoapCall";

    public static final String URL = "http://192.168.1.10:8090/samWebService.asmx";
    public static final String NAMESPACE = "http://tempuri.org/";

    public static final String METHOD_GET_PRODUCT = "GetProductByTechNo";
    public static final String METHOD_EXECUTE_MIDTERM_COUNT = "ExecuteCycleCount";
    public static final String METHOD_GET_DRAFT_LIST = "GetListHavale";
    public static final String METHOD_GET_PERM_LIST = "GetListMojavez";
    public static final String METHOD_GET_RECEIPT_LIST = "GetListResid";
    public static final String METHOD_INSERT_PRODUCT = "InsertProduct";
    public static final String METHOD_UPDATE_MIDTERM_COUNT = "UpdateCycleCount";
    public static final String METHOD_UPDATE_SHELF = "UpdateProductShelf";
    public static final String METHOD_GET_BUSINESS_DETAILS = "GetBusinessDetails";
    public static final String METHOD_GET_CARDEX = "GetCardex";
    public static final String METHOD_GET_COUNTING_REG_LIST = "GetCycleCount";
    public static final String METHOD_GET_CYCLE_COUNT_MIDDLE = "GetCycleCountMiddle";
    public static final String METHOD_DELETE_CYCLE_COUNT = "DeleteCycleCount";
    public static final String METHOD_GET_LOGIN_INFO = "GetLoginInfo";
    public static final String METHOD_GET_UNIT_INFO = "GetUnitInfo";
    public static final String METHOD_GET_PRODUCT_TYPE_INFO = "GetProductTypeInfo";
    public static final String METHOD_GET_DRAFT_FROM_PERM = "HavalehFromMojavez";
    public static final String METHOD_ACCEPT_BUSINESS = "AcceptBusiness";
    public static final String METHOD_UPDATE_COUNTING_REG = "UpdateCycleCountkol";
    public static final String METHOD_EXECUTE_TEMP_BUSINESS_DETAILS = "usp_ExecTempBusinessDetail";
    public static final String METHOD_GET_PERM_LIST_TO_CONVERT = "GetListMojavezForConvert";
    public static final String METHOD_GET_DRAFT_TYPES = "GetHavalehTypes";
    public static final String METHOD_GET_TEMP_BUSINESS_DETAILS = "GetTempBusinessDetails";

    public static final int TIMEOUT = 15000;

    private AppCompatActivity activity;
    private String method;
    private String soapAction;
    private JSONObject childObject = null;
    private JSONArray jsonArray = null;
    private ArrayList<JSONObject> childObjectList = null;


    public SoapCall(AppCompatActivity activity, @NonNull String method) {
        this.activity = activity;
        this.method = method;
        this.soapAction = NAMESPACE + method;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (activity != null) {
            new CustomProgressBar(activity).showProgress();
        }
    }


    @Override
    protected ArrayList<JSONObject> doInBackground(PropertyInfo... propertyInfos) {
        SoapObject request = new SoapObject(NAMESPACE, method);
        for (PropertyInfo p : propertyInfos) {
            request.addProperty(p);
        }


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transportSE = new HttpTransportSE(URL, TIMEOUT);

        try {
            transportSE.call(soapAction, envelope);

            SoapObject ss = (SoapObject) envelope.bodyIn;

            // publishProgress(ss.toString());
            if (ss.getPropertyCount() >= 1) {

                if (ss.getProperty(0).toString().equals("true")) {
                    childObjectList = new ArrayList<>();
                    String ans = "[{\"boolean\":\"true\" }]";
                    jsonArray = new JSONArray(ans);
                    childObject = jsonArray.getJSONObject(0);
                    childObjectList.add(childObject);
                    // publishProgress(childObject.getString("boolean"));
                } else if (ss.getProperty(0).toString().equals("false")) {
                    childObjectList = new ArrayList<>();
                    String ans = "[{\"boolean\":\"false\" }]";
                    jsonArray = new JSONArray(ans);
                    childObject = jsonArray.getJSONObject(0);
                    childObjectList.add(childObject);
                } else {
                    childObjectList = new ArrayList<>(ss.getPropertyCount());
                    jsonArray = new JSONArray(ss.getProperty(0).toString());
                    for (int k = 0; k < jsonArray.length(); k++) {
                        childObject = jsonArray.getJSONObject(k);
                        childObjectList.add(childObject);
                    }
                }
            }

        } catch (IOException | JSONException | XmlPullParserException e) {
            e.printStackTrace();
            Log.e(TAG, "doInBackground: " + e.getLocalizedMessage());
        }
        return childObjectList;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        // Toast.makeText(MainActivity.this, values[0].toString(), Toast.LENGTH_LONG).show();
        // tv.setText(values[0].toString());
    }

    @Override
    protected void onPostExecute(ArrayList<JSONObject> jsonObjects) {
        super.onPostExecute(jsonObjects);
        //  if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (activity != null) {
            new CustomProgressBar(activity).dismissProgress();
        }
    }
}