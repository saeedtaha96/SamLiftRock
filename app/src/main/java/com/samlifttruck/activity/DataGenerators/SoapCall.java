package com.samlifttruck.activity.DataGenerators;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class SoapCall extends AsyncTask<PropertyInfo, Object, ArrayList<JSONObject>> {
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
    public static final String METHOD_GET_BUSINESS_DETAILS = "GetBusinessDetails";
    public static final String METHOD_GET_CARDEX = "GetCardex";
    public static final String METHOD_GET_CYCLE_COUNT = "GetCycleCount";
    public static final String METHOD_GET_CYCLE_COUNT_MIDDLE = "GetCycleCountMiddle";
    public static final String METHOD_DELETE_CYCLE_COUNT = "DeleteCycleCount";
    public static final String METHOD_GET_LOGIN_INFO = "GetLoginInfo";

    public static final String SOAP_ACTION_GET_PRODUCT = NAMESPACE + METHOD_GET_PRODUCT;
    public static final String SOAP_ACTION_EXECUTE_MIDTERM_COUNT = NAMESPACE + METHOD_EXECUTE_MIDTERM_COUNT;
    public static final String SOAP_ACTION_GET_DRAFT_LIST = NAMESPACE + METHOD_GET_DRAFT_LIST;
    public static final String SOAP_ACTION_GET_PERM_LIST = NAMESPACE + METHOD_GET_PERM_LIST;
    public static final String SOAP_ACTION_GET_RECEIPT_LIST = NAMESPACE + METHOD_GET_RECEIPT_LIST;
    public static final String SOAP_ACTION_INSERT_PRODUCT = NAMESPACE + METHOD_INSERT_PRODUCT;
    public static final String SOAP_ACTION_LOGIN_CHECK = NAMESPACE + METHOD_LOGIN_CHECK;
    public static final String SOAP_ACTION_UPDATE_MIDTERM_COUNT = NAMESPACE + METHOD_UPDATE_MIDTERM_COUNT;
    public static final String SOAP_ACTION_UPDATE_SHELF = NAMESPACE + METHOD_UPDATE_SHELF;
    public static final String SOAP_ACTION_GET_BUSINESS_DETAILS = NAMESPACE + METHOD_GET_BUSINESS_DETAILS;
    public static final String SOAP_ACTION_GET_CARDEX = NAMESPACE + METHOD_GET_CARDEX;
    public static final String SOAP_ACTION_GET_CYCLE_COUNT = NAMESPACE + METHOD_GET_CYCLE_COUNT;
    public static final String SOAP_ACTION_GET_CYCLE_COUNT_MIDDLE = NAMESPACE + METHOD_GET_CYCLE_COUNT_MIDDLE;
    public static final String SOAP_ACTION_DELETE_CYCLE_COUNT = NAMESPACE + METHOD_DELETE_CYCLE_COUNT;
    public static final String SOAP_ACTION_GET_LOGIN_INFO = NAMESPACE + METHOD_GET_LOGIN_INFO;

    public static final int TIMEOUT = 15000;

    private ProgressBar progressBar;
    private String method;
    private String soapAction;
    private JSONObject childObject = null;
    private JSONArray jsonArray = null;
    private ArrayList<JSONObject> childObjectList = null;


    public SoapCall(ProgressBar progressBar, String method, String soapAction) {
        this.progressBar = progressBar;
        this.method = method;
        this.soapAction = soapAction;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
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
                childObjectList = new ArrayList<>(ss.getPropertyCount());

                for (int k = 0; k < ss.getPropertyCount(); k++) {
                    jsonArray = new JSONArray(ss.getProperty(k).toString());
                    childObject = jsonArray.getJSONObject(0);
                    childObjectList.add(childObject);
                }
            }

        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        if (progressBar != null) progressBar.setVisibility(View.GONE);

    }
}