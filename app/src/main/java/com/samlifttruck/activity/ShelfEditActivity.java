package com.samlifttruck.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.Result;
import com.samlifttruck.R;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ShelfEditActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    TextInputEditText etFanniNumb;
    ProgressBar progressBar;
    TextView tvProductName;
    TextView tvShelfNum;
    Button btnConfirm;
    private EditText etShelfNum;
    private int myProductCode;
    private static final int NOT_FOUND_CODE = -404;
    List<JSONObject> list = null;

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
                            etFanniNumb.requestFocus();
                        } else {
                            getProduct();
                        }

                        closeKeyPad();

                        return true;
                    }
                }
                return false;
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etShelfNum.getText().toString().equals("")){
                    etShelfNum.setError("خالی است");
                    etShelfNum.requestFocus();
                }
                else if((!tvProductName.getText().toString().equals("")) & (!tvProductName.getText().toString().equals("-")) & (myProductCode != NOT_FOUND_CODE)){
                    setProductNewShelfNum();
                }
            }
        });

    }

    private void setProductNewShelfNum() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("productCode");
        p1.setValue(myProductCode);
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("shelf");
        if(etShelfNum.getText().toString().equals("")) {
            etShelfNum.setText(" ");
        }
        p2.setValue(etShelfNum.getText().toString());
        p2.setType(String.class);

        final SoapCall ss = new SoapCall(progressBar, SoapCall.METHOD_UPDATE_SHELF, SoapCall.SOAP_ACTION_UPDATE_SHELF);
        ss.execute(p0, p1, p2);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ss.get() != null) {
                        list = ss.get();
                        if (list.get(0).getString("boolean").equals("true")) {
                            Toast.makeText(ShelfEditActivity.this, "مورد با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                            etShelfNum.setText("");
                            tvShelfNum.setText("-");
                            tvProductName.setText("-");
                            etFanniNumb.setText("");
                            etFanniNumb.requestFocus();
                        } else if (list.get(0).toString().equals("false")) {
                            Toast.makeText(ShelfEditActivity.this, "خطا در ثبت", Toast.LENGTH_LONG).show();

                        }

                    } else if (ss.get() == null) {
                        Toast.makeText(ShelfEditActivity.this, "خطا در ثبت", Toast.LENGTH_SHORT).show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getProduct() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("techNo");
        p1.setValue(etFanniNumb.getText().toString());
        p1.setType(String.class);

        final SoapCall ss = new SoapCall(progressBar, SoapCall.METHOD_GET_PRODUCT, SoapCall.SOAP_ACTION_GET_PRODUCT);
        ss.execute(p0, p1);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ss.get() != null) {
                        list = ss.get();
                        tvProductName.setText(list.get(0).getString("ProductName"));
                        tvShelfNum.setText(list.get(0).getString("shelf"));
                        myProductCode = list.get(0).getInt("productCode");
                        etShelfNum.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(etShelfNum, InputMethodManager.SHOW_IMPLICIT);
                    } else if (ss.get() == null) {
                        tvProductName.setText(getResources().getText(R.string.dash));
                        tvShelfNum.setText(getResources().getText(R.string.dash));
                        myProductCode = NOT_FOUND_CODE;
                        Toast.makeText(ShelfEditActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        etShelfNum = findViewById(R.id.activity_midterm_et_input_new);
        btnConfirm = findViewById(R.id.activity_shelf_edit_btn_confirm);
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
                .setPositiveButton(getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkQRcodePremission();
                    }
                })
                .setNegativeButton(getString(R.string.txt_cancel), null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        // String myResult = result.getText();
        etFanniNumb.setText(result.getText());
        getProduct();
        onResume();


    }


    public void onBackBtnClick(View view) {
        finish();
    }

}




