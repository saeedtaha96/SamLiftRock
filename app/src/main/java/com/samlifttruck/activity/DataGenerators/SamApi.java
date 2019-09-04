package com.samlifttruck.activity.DataGenerators;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.samlifttruck.activity.Models.ProductModel;
import com.samlifttruck.activity.ShelfEditActivity;

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
import java.util.List;
import java.util.Map;


public class SamApi {

    private static final String PRODUCT_SOAP_ACTION = "http://tempuri.org/GetProductByTechNoj";
    private static final String LOGIN_SOAP_ACTION = "http://tempuri.org/LoginCheck";
    private static final String METHOD_GET_PRODUCT = "GetProductByTechNoj";
    private static final String METHOD_LOGIN = "LoginCheck";
    private static final String URL = "http://192.168.1.10:8090/samWebService.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final int TIMEOUT = 15000;


    public class SoapCallProduct extends AsyncTask<String, Object, String> {
        String response;
        ProgressBar progressBar;
        ProductModel pm;
        String method;

        public SoapCallProduct(String method, Map<String, Integer>... map) {

            this.method = method;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_GET_PRODUCT);
            PropertyInfo p = new PropertyInfo();
            p.setName("passCode");
            p.setValue(strings[0]);
            p.setType(String.class);
            request.addProperty(p);

            PropertyInfo p2 = new PropertyInfo();
            p2.setName("techNo");
            p2.setValue(strings[1]);
            p2.setType(String.class);
            request.addProperty(p2);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(URL, TIMEOUT);
            try {
                transportSE.call(PRODUCT_SOAP_ACTION, envelope);
                SoapObject ss = (SoapObject) envelope.bodyIn;
                if (ss != null) {
                    response = ss.getProperty(0).toString();
                }


            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);

            pm = new ProductModel();
            JSONObject childObject = null;
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(s);
                childObject = jsonArray.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                if (childObject != null) {
                    //  m.setsProductCode(childObject.getString("ProductCode"));
                    // m.setsTechNo(childObject.getString("TechNo"));
                    pm.setsProductName(childObject.getString("ProductName"));
                    pm.setsShelf(childObject.getString("Shelf"));

                   /* m.setsMainUnitID(childObject.getString("MainUnitID"));
                    m.setsProductTypeID(childObject.getString("ProductTypeID"));
                    m.setOrderPoint(childObject.getString("OrderPoint"));
                    m.setOrderCount(childObject.getString("OrderCount"));
                    m.setDescription(childObject.getString("Description"));
                    m.setSerialNo(childObject.getString("SerialNo"));
                    m.setS91(childObject.getString("s91"));
                    m.setS92(childObject.getString("s92"));
                    m.setWithTax(childObject.getString("withTax"));*/
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //store your url in some list

          /*  if (s != null) {
                String answer;
                answer = "proCode = " + m.getsProductCode() + "\n";
                answer += "techNo = " + m.getsTechNo() + "\n";
                answer += "pName = " + m.getsProductName() + "\n";
                answer += "mainUnit = " + m.getsMainUnitID() + "\n";
                answer += "proType = " + m.getsProductTypeID() + "\n";
                answer += "shelf = " + m.getsShelf() + "\n";
                //   tv.setText(answer);

            } else {
                // tv.setText("error");
            }*/
        }

        public ProductModel getList() {
            return pm;
        }
    }
}
