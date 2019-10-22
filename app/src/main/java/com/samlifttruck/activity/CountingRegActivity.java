package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.Result;
import com.samlifttruck.R;
import com.samlifttruck.activity.DataGenerators.ConstCntReg;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.CountingRegModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

import lib.kingja.switchbutton.SwitchMultiButton;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class CountingRegActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private SwitchMultiButton mSwitchMultiButton;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private TextInputEditText etFanniNumb;
    private TextInputEditText etProductName, etShelfNum, etCounting1, etCounting2, etCounting3, etResult_1_2, etFinalResult, etInventory;
    private Button btnSave;
    private int myProductCode;
    private ImageButton btnCountingRegList;
    List<JSONObject> list = null;
    ProgressBar progressBar;
    CountingRegModel model;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_reg);


        setupViews();
        setToolbarText();
        checkQRcodePremission();

        getActivityBundle();
        etCounting2.setEnabled(false);
        etCounting3.setEnabled(false);

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
                            //  new ShelfEditActivity.soapCall().execute("x4fg54-D9ib", etFanniNumb.getText().toString());
                            getProduct();
                        }

                        closeKeyPad();

                        return true;
                    }
                }
                return false;
            }
        });


        btnCountingRegList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CountingRegActivity.this, CountingRegListActivity.class));
            }
        });

        mSwitchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                if (position == 2) {
                    etCounting1.setEnabled(true);
                    etCounting1.requestFocus();
                    etCounting2.setEnabled(false);
                    etCounting3.setEnabled(false);

                } else if (position == 1) {
                    etCounting2.setEnabled(true);
                    etCounting2.requestFocus();
                    etCounting1.setEnabled(false);
                    etCounting3.setEnabled(false);

                } else if (position == 0) {
                    etCounting3.setEnabled(true);
                    etCounting3.requestFocus();
                    etCounting1.setEnabled(false);
                    etCounting2.setEnabled(false);
                }

            }
        });

    }

    private CountingRegModel getActivityBundle() {
        CountingRegModel myModel = new CountingRegModel();
        if(getIntent().getExtras() != null){
            Bundle extras = getIntent().getExtras();

            etInventory.setText(extras.getInt(ConstCntReg.ON_HAND));
            etProductName.setText(extras.getInt(ConstCntReg.PRODUCT_NAME));
            etFanniNumb.setText(extras.getInt(ConstCntReg.TECH_NO));
            myModel.setOnHand(extras.getInt(ConstCntReg.SHELF_NUM));
            myModel.setOnHand(extras.getInt(ConstCntReg.COUNT_1));
            myModel.setOnHand(extras.getInt(ConstCntReg.COUNT_2));
            myModel.setOnHand(extras.getInt(ConstCntReg.COUNT_3));
            myModel.setOnHand(extras.getInt(ConstCntReg.RESULT_1_2));
            myModel.setOnHand(extras.getInt(ConstCntReg.FINAL_RESULT));
            myModel.setProductCode(extras.getInt(ConstCntReg.PRODUCT_CODE));
        }
        return myModel;
    }

    private void setupViews() {
        etFanniNumb = findViewById(R.id.layout_et_tech_no);
        mSwitchMultiButton = findViewById(R.id.activity_counting_reg_switchbutton);
        scannerView = findViewById(R.id.scanner_counting_reg);
        etProductName = findViewById(R.id.activity_counting_reg_product_name);
        etShelfNum = findViewById(R.id.activity_counting_reg_shelf_num);
        etCounting1 = findViewById(R.id.activity_counting_reg_counting_1);
        etCounting2 = findViewById(R.id.activity_counting_reg_counting_2);
        etCounting3 = findViewById(R.id.activity_counting_reg_counting_3);
        etResult_1_2 = findViewById(R.id.activity_counting_reg_result_1_2);
        etFinalResult = findViewById(R.id.activity_counting_reg_final_result);
        btnSave = findViewById(R.id.activity_counting_reg_btn_save);
        btnCountingRegList = findViewById(R.id.activity_imgv_today_list);
        etInventory = findViewById(R.id.activity_counting_reg_inventory);
    }

    void closeKeyPad() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.sabt_shomaresh));
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
        new AlertDialog.Builder(CountingRegActivity.this)
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

    private void getProduct() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("techNo");
        p1.setValue(etFanniNumb.getText().toString());
        p1.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_PRODUCT);
        ss.execute(p0, p1);
        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    list = ss.get();

                    CountingRegActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (list != null) {

                                    etProductName.setText(list.get(0).getString("ProductName"));
                                    etShelfNum.setText(list.get(0).getString("shelf"));
                                    myProductCode = list.get(0).getInt("productCode");
                                    etInventory.setText(list.get(0).getString("onHand"));
                                    etCounting1.requestFocus();
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(etShelfNum, InputMethodManager.SHOW_IMPLICIT);
                                } else {
                                    etCounting1.setText("");
                                    etCounting2.setText("");
                                    etCounting3.setText("");
                                    etShelfNum.setText(getString(R.string.whitespace));
                                    etProductName.setText(getString(R.string.whitespace));
                                    etInventory.setText(getString(R.string.zero));
                                    etFinalResult.setText(getString(R.string.zero));
                                    etResult_1_2.setText(getString(R.string.zero));
                                    myProductCode = Utility.NOT_FOUND_CODE;
                                    Toast.makeText(CountingRegActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void onBackBtnClick(View view) {
        finish();
    }
}
