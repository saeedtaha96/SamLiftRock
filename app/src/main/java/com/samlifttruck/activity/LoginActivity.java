package com.samlifttruck.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.READ_PHONE_STATE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;
    private String imei;
    List<JSONObject> list = null;
    ProgressBar progressBar;
    private TextView btnRegDevice;
    private static final int REQUEST_PHONE_STATE = 10;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("myprefs", MODE_PRIVATE);
        if (pref.contains(Utility.IS_LOGIN)) {
            if (pref.getBoolean(Utility.IS_LOGIN, false)) {
                startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                finish();
            }
        }
        setContentView(R.layout.activity_login);

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Utility.IS_LOGIN, false);
        editor.apply();

        setupView();
        btnLogin.setOnClickListener(this);
        btnRegDevice.setOnClickListener(this);

        getPremission();
        getDeviceId();
    }

    private boolean getPremission() {
        boolean state = false;
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                // Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
                state = true;
            } else {
                requestPermission();
                state = false;
            }
        }
        return state;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (checkPermission()) {
            String ts = Context.TELEPHONY_SERVICE;
            TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(ts);
            imei = mTelephonyMgr.getDeviceId();
            btnRegDevice.setPaintFlags(btnRegDevice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            //   deviceId.setText(imei);

        } else {
            // requestPermission();
        }
    }


    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE}, REQUEST_PHONE_STATE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_STATE:
                if (grantResults.length > 0) {

                    boolean stateAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (stateAccepted) {
                        Toast.makeText(getApplicationContext(), getString(R.string.state_toast_premission_granted_persian), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.state_toast_premission_denied_persian), Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_PHONE_STATE)) {
                                showMessageOKCancel((getString(R.string.state_toast_premission_allow_persian)),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{READ_PHONE_STATE},
                                                        REQUEST_PHONE_STATE);
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
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("اجازه", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getPremission();
                    }
                })
                .setNegativeButton(getString(R.string.txt_cancel), null)
                .create()
                .show();
    }

    private void getDeviceId() {

    }


    private void setupView() {
        progressBar = findViewById(R.id.activity_login_pbar);
        etUsername = findViewById(R.id.login_input_username);
        etPassword = findViewById(R.id.login_input_password);
        btnLogin = findViewById(R.id.login_btn_login);
        btnRegDevice = findViewById(R.id.activity_login_tv_reg_device);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.activity_login_tv_reg_device:

                if (getPremission()) {
                    iOSDialogBuilder dialog = Utility.newIOSdialog(LoginActivity.this);

                    dialog.
                            setTitle("کد: " + "" + imei)
                            .setSubtitle(getString(R.string.txt_reg_device) + "\n" + getString(R.string.txt_reg_device_send_email))
                            .setPositiveListener(getString(R.string.txt_email), new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    final Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.
                                            setData(Uri.parse("mailto:" + "ma.fatemi@gmail.com")).
                                            putExtra(Intent.EXTRA_SUBJECT, "Sam IMEI code").
                                            putExtra(Intent.EXTRA_TEXT, imei);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeListener(getString(R.string.txt_ok), new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .build().show();
                } else {
                    Toast.makeText(this, getString(R.string.txt_need_permission), Toast.LENGTH_SHORT).show();
                }

                break;

            // btn Login
            case R.id.login_btn_login:
                closeKeyPad();

                if (isFilled()) {
                    PropertyInfo p0 = new PropertyInfo();
                    p0.setName("passCode");
                    p0.setValue("x4fg54-D9ib");
                    p0.setType(String.class);

                    PropertyInfo p1 = new PropertyInfo();
                    p1.setName("uName");
                    p1.setValue(etUsername.getText().toString());
                    p1.setType(String.class);

                    PropertyInfo p2 = new PropertyInfo();
                    p2.setName("uPass");
                    p2.setValue(etPassword.getText().toString());
                    p2.setType(String.class);

                    PropertyInfo p3 = new PropertyInfo();
                    p3.setName("mobilekey");
                    p3.setValue(imei);
                    p3.setType(String.class);


                    final SoapCall ss = new SoapCall(progressBar, SoapCall.METHOD_GET_LOGIN_INFO, SoapCall.SOAP_ACTION_GET_LOGIN_INFO);
                    ss.execute(p0, p1, p2, p3);


                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (ss.get() != null) {
                                    list = ss.get();
                                    for (int i = 0; i < list.size(); i++) {
                                        SharedPreferences.Editor edt = pref.edit();
                                        edt.putString(Utility.LOGIN_USERNAME, list.get(i).getString("FullName"));
                                        edt.putInt(Utility.LOGIN_WORKGROUP_ID,list.get(i).getInt("WorkgroupID"));
                                        edt.putInt(Utility.LOGIN_USER_ID,list.get(i).getInt("UserID"));
                                        edt.putBoolean(Utility.IS_LOGIN, true);
                                        edt.apply();
                                        startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                                        finish();
                                        break;
                                        //    tv.append("tech no = " + list.get(i).getString("techNo") + "\n");
                                        //   tv.append("product = " + list.get(i).getString("ProductName"));
                                        //  System.out.println(ss.get());
                                    }
                                } else if (ss.get() == null) {
                                    iOSDialogBuilder ios = Utility.newIOSdialog(LoginActivity.this);
                                    ios.setTitle(getString(R.string.txt_error)).setPositiveListener(getString(R.string.txt_ok), new iOSDialogClickListener() {
                                        @Override
                                        public void onClick(iOSDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    })
                                            .setSubtitle(getString(R.string.txt_wrong_name_pass))
                                            .build().show();
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
            default:
                ;
        }
    }

    private boolean isFilled() {
        if (etUsername.getText().toString().equals("")) {
            etUsername.setError(getString(R.string.txt_please_enter_username));
            etUsername.requestFocus();
            return false;
        } else if (etPassword.getText().toString().equals("")) {
            etPassword.setError(getString(R.string.txt_please_enter_password));
            etPassword.requestFocus();
            return false;

        } else if (imei == null) {
            iOSDialogBuilder ios = Utility.newIOSdialog(this);
            ios.setTitle(getString(R.string.txt_error))
                    .setSubtitle(getString(R.string.txt_imei_not_registered))
                    .setPositiveListener(getString(R.string.txt_eta), new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            requestPermission();
                            dialog.dismiss();
                        }
                    }).setNegativeListener(getString(R.string.txt_cancel), new iOSDialogClickListener() {
                @Override
                public void onClick(iOSDialog dialog) {
                    dialog.dismiss();
                }
            }).build().show();
            return false;
        } else {
            return true;
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

}
