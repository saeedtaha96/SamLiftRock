package com.samlifttruck.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MidtermControlActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private TextInputEditText etFanniNumb;
    private int workgroupID;
    private ImageView btnTodayList;
    private TextView tvInventoryRelative;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private SharedPreferences pref;
    private ProgressBar progressBar;
    private int myProductCode = -404;
    private String myTechNo = "-404";
    private List<JSONObject> list = null;
    EditText etCurrentCount;
    Button btnSave;
    private TextView tvProductName, tvShelfNum, tvUnit, tvInventory;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midterm_counting);

        setupViews();
        setToolbarText();
        checkQRcodePremission();

        pref = getSharedPreferences("myprefs", MODE_PRIVATE);
        workgroupID = pref.getInt(Utility.LOGIN_WORKGROUP_ID, 56);
        if (workgroupID == 56) {
            tvInventoryRelative.setVisibility(View.GONE);
            tvInventory.setVisibility(View.GONE);
        }

        btnTodayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MidtermControlActivity.this, MidtermTodayListActivity.class));
            }
        });
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myProductCode == -404 | myTechNo.equals("-404") | etFanniNumb.getText().toString().equals("")) {
                    Toast.makeText(MidtermControlActivity.this, "آیتمی برای ویرایش موجود نیست", Toast.LENGTH_SHORT).show();
                } else if (etCurrentCount.getText().toString().equals("")) {
                    etCurrentCount.setError("خالی است");
                    etCurrentCount.requestFocus();
                } else {
                    updateProduct();
                }
            }
        });

    }

    private void setupViews() {
        btnSave = findViewById(R.id.activity_midterm_counting_btn_save);
        progressBar = findViewById(R.id.activity_midterm_pbar);
        tvInventoryRelative = findViewById(R.id.relative_midterm_tv_inventory);
        tvInventory = findViewById(R.id.activity_midterm_counting_inventory);
        scannerView = findViewById(R.id.scanner_midterm);
        etFanniNumb = findViewById(R.id.layout_et_tech_no);
        btnTodayList = findViewById(R.id.activity_imgv_today_list);
        tvShelfNum = findViewById(R.id.activity_midterm_tv_shelf_number);
        tvUnit = findViewById(R.id.activity_midterm_tv_unit);
        tvProductName = findViewById(R.id.activity_midterm_tv_product_name);
        etCurrentCount = findViewById(R.id.activity_midterm_et_input_new);
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

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.menu_txt_control));
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
        } else {
            Toast.makeText(getApplicationContext(), "This feature is not supported on Android API lower than 23", Toast.LENGTH_LONG).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    finish();
                }
            }, 3000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
        new AlertDialog.Builder(MidtermControlActivity.this)
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
    public void handleResult(Result result) {
        final String myResult = result.getText();
        etFanniNumb.setText(myResult);
        getProduct();
        onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onBackBtnClick(View view) {
        finish();
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

        final SoapCall ss = new SoapCall(progressBar, SoapCall.METHOD_GET_PRODUCT);
        ss.execute(p0, p1);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ss.get() != null) {
                        list = ss.get();
                        tvProductName.setText(list.get(0).getString("ProductName"));
                        tvShelfNum.setText(list.get(0).getString("shelf"));
                        tvInventory.setText(list.get(0).getString("onHand"));
                        tvUnit.setText(list.get(0).getString("MainUnitID"));
                        myProductCode = list.get(0).getInt("productCode");
                        myTechNo = etFanniNumb.getText().toString().trim();
                        etCurrentCount.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(etCurrentCount, InputMethodManager.SHOW_IMPLICIT);
                    } else if (ss.get() == null) {
                        tvProductName.setText(getResources().getText(R.string.dash));
                        tvShelfNum.setText(getResources().getText(R.string.dash));
                        tvUnit.setText(getResources().getText(R.string.dash));
                        tvInventory.setText(getResources().getText(R.string.dash));
                        etCurrentCount.setText("");
                        myProductCode = Utility.NOT_FOUND_CODE;
                        myTechNo = String.valueOf(Utility.NOT_FOUND_CODE);
                        Toast.makeText(MidtermControlActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                    }

                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MidtermControlActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateProduct() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("techNo");
        p1.setValue(myTechNo);
        p1.setType(String.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("productName");
        p2.setValue(tvProductName.getText().toString());
        p2.setType(String.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("unitId");
        p3.setValue(Integer.valueOf(tvUnit.getText().toString()));
        p3.setType(Integer.class);

        PropertyInfo p4 = new PropertyInfo();
        p4.setName("onHand");
        p4.setValue(Integer.valueOf(tvInventory.getText().toString()));
        p4.setType(Integer.class);

        PropertyInfo p5 = new PropertyInfo();
        p5.setName("currentCount");
        p5.setValue(Integer.valueOf(etCurrentCount.getText().toString()));
        p5.setType(Integer.class);

        PropertyInfo p6 = new PropertyInfo();
        p6.setName("shelf");
        p6.setValue(tvShelfNum.getText().toString());
        p6.setType(String.class);

        PropertyInfo p7 = new PropertyInfo();
        p7.setName("productCode");
        p7.setValue(myProductCode);
        p7.setType(Integer.class);

        final SoapCall ss = new SoapCall(progressBar, SoapCall.METHOD_UPDATE_MIDTERM_COUNT);
        ss.execute(p0, p1, p2, p3, p4, p5, p6, p7);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ss.get() != null) {
                        list = ss.get();
                        if (list.get(0).getString("boolean").equals("true")) {
                            Toast.makeText(MidtermControlActivity.this, "مورد با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                            etCurrentCount.setText("");
                            tvShelfNum.setText("-");
                            tvProductName.setText("-");
                            tvInventory.setText("-");
                            tvUnit.setText("-");
                            etFanniNumb.setText("");
                            etFanniNumb.requestFocus();
                        }
                    } else if (ss.get() == null) {
                        Toast.makeText(MidtermControlActivity.this, "خطا در ثبت", Toast.LENGTH_SHORT).show();
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
}





