package com.samlifttruck.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.Result;
import com.samlifttruck.R;
import com.samlifttruck.activity.Models.ProductModel;
import com.samlifttruck.activity.DataGenerators.Utility.SOAP;

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

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ShelfEditActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {



    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    TextInputEditText etFanniNumb;
    ProgressBar progressBar;
    TextView tvProductName;
    TextView tvShelfNum;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_edit);

        setupViews();
        setToolbarText();
        checkQRcodePremission();
        etFanniNumb.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() <= (etFanniNumb.getLeft() + (2 * etFanniNumb.getCompoundDrawables()[DRAWABLE_LEFT].getDirtyBounds().width()))) {
                        // your action here
                        if (etFanniNumb.getText().toString().equals("")) {
                            etFanniNumb.setError("خالی است");
                        } else {
                            new soapCall().execute("x4fg54-D9ib", etFanniNumb.getText().toString());
                        }

                        closeKeyPad();

                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void closeKeyPad() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void setupViews() {
        scannerView = findViewById(R.id.scanner_shelf);
        etFanniNumb = findViewById(R.id.layout_et_tech_no);
        progressBar = findViewById(R.id.activity_shelf_pbar);
        tvProductName = findViewById(R.id.activity_shelf_tv_product_name);
        tvShelfNum = findViewById(R.id.activity_shelf_tv_shelf_number);

    }

    public void checkQRcodePremission() {
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                //  Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_shelf_edit));
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {

                    setContentView(R.layout.activity_shelf_edit);
                    scannerView = findViewById(R.id.scanner_midterm);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                // requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getApplicationContext(), getString(R.string.state_toast_premission_granted_persian), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.state_toast_premission_denied_persian), Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel((getString(R.string.state_toast_premission_allow_persian)),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }

                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ShelfEditActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkQRcodePremission();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        // String myResult = result.getText();
        etFanniNumb.setText(result.getText());
        onResume();


    }


    public void onBackBtnClick(View view) {
        finish();
    }

    class soapCall extends AsyncTask<String, Object, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(SOAP.NAMESPACE, SOAP.METHOD_GET_PRODUCT);
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

            HttpTransportSE transportSE = new HttpTransportSE(SOAP.URL, SOAP.TIMEOUT);

            try {
                transportSE.call(SOAP.SOAP_ACTION_GET_PRODUCT, envelope);

                SoapObject ss = (SoapObject) envelope.bodyIn;
                if (ss.getPropertyCount() >= 1) {
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
            ProductModel m = new ProductModel();
            if (s != null) {
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
                        m.setsProductCode(childObject.getString("ProductCode"));
                        m.setsTechNo(childObject.getString("TechNo"));
                        m.setsProductName(childObject.getString("ProductName"));
                        m.setsMainUnitID(childObject.getString("MainUnitID"));
                        m.setsProductTypeID(childObject.getString("ProductTypeID"));
                        m.setsShelf(childObject.getString("Shelf"));
                        m.setOrderPoint(childObject.getString("OrderPoint"));
                        m.setOrderCount(childObject.getString("OrderCount"));
                        m.setDescription(childObject.getString("Description"));
                        m.setSerialNo(childObject.getString("SerialNo"));
                        m.setS91(childObject.getString("s91"));
                        m.setS92(childObject.getString("s92"));
                        m.setWithTax(childObject.getString("withTax"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //store your url in some list

            if (s != null) {
                tvProductName.setText(m.getsProductName());
                tvShelfNum.setText(m.getsShelf());
            } else {
                tvProductName.setText(getResources().getText(R.string.dash));
                tvShelfNum.setText(getResources().getText(R.string.dash));
                Toast.makeText(ShelfEditActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
            }
        }
    }
}




