package com.samlifttruck.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.Result;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CustomSpinnerAdapter;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class CardexActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private Spinner spinnerDateChoose;
    private CheckBox chbxSetDate;
    private TextInputEditText etFanniNumb;
    private PersianCalendar calendar;
    private int year;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardex);

        findViews();
        setToolbarText();
        initCustomSpinner();
        checkQRcodePremission();

        chbxSetDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    spinnerDateChoose.setBackground(getDrawable(R.drawable.spinner_cars));
                    spinnerDateChoose.setEnabled(true);
                } else {
                    spinnerDateChoose.setBackground(getDrawable(R.drawable.spinner_cars_uncheck));
                    spinnerDateChoose.setEnabled(false);
                }
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
                        String s;
                        if (etFanniNumb.getText().toString().equals("")) {
                            etFanniNumb.setError("خالی است");
                            etFanniNumb.requestFocus();
                        } else {
                            if (chbxSetDate.isChecked()) {
                                s = spinnerDateChoose.getSelectedItem().toString();
                                Toast.makeText(CardexActivity.this, s, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CardexActivity.this,CardexDeatilsActivity.class));
                            } else {
                                s = String.valueOf(year);
                                Toast.makeText(CardexActivity.this, s, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CardexActivity.this,CardexDeatilsActivity.class));
                                // new ShelfEditActivity.soapCall().execute("x4fg54-D9ib", etFanniNumb.getText().toString());
                            }

                        }

                        closeKeyPad();

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_cardex));
    }

    private void initCustomSpinner() {
        spinnerDateChoose.setBackground(getDrawable(R.drawable.spinner_cars_uncheck));
        spinnerDateChoose.setEnabled(false);
        calendar = new PersianCalendar();
        year = calendar.getPersianYear();

        String[] years = new String[year - 1390];

        for (int i = 1; i <= (year - 1390); i++) {
            years[i - 1] = String.valueOf(year - i);
        }

        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(this, years);
        spinnerDateChoose.setAdapter(spinnerAdapter);
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
        new AlertDialog.Builder(CardexActivity.this)
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
        onResume();


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

    private void findViews() {
        scannerView = findViewById(R.id.scanner_cardex);
        chbxSetDate = findViewById(R.id.activity_cardex_checkbox);
        spinnerDateChoose = findViewById(R.id.spinner_cardex);
        etFanniNumb = findViewById(R.id.layout_et_tech_no);
    }


    public void onBackBtnClick(View view) {
        finish();
    }
}
