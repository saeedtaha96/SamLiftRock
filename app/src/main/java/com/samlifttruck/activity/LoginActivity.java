package com.samlifttruck.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;

import static android.Manifest.permission.READ_PHONE_STATE;

public class LoginActivity extends AppCompatActivity implements View.OnLongClickListener {
    TextInputEditText etUsername, etPassword;
    Button btnLogin;
    TextView deviceId;
    private static final int REQUEST_PHONE_STATE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupView();
        setupListeners();

        deviceId.setOnLongClickListener(this);

        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
               // Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }

        getDeviceId();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (checkPermission()) {
            String ts = Context.TELEPHONY_SERVICE;
            TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(ts);
            String imei = mTelephonyMgr.getDeviceId();
            deviceId.setPaintFlags(deviceId.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
         //   deviceId.setText(imei);

        } else {
            requestPermission();
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
                .setPositiveButton("اجازه", okListener)
                .setNegativeButton("لغو", null)
                .create()
                .show();
    }

    private void getDeviceId() {

    }


    private void setupView() {
        etUsername = findViewById(R.id.login_input_username);
        etPassword = findViewById(R.id.login_input_password);
        btnLogin = findViewById(R.id.login_btn_login);
        deviceId = findViewById(R.id.device_id);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onLongClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.device_id :  final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("mailto:"+"taha@gmail.com"))
                        .putExtra(Intent.EXTRA_SUBJECT, "Sam Lift Rock IMEI CODE")
                        .putExtra(Intent.EXTRA_TEXT, deviceId.getText().toString());
                startActivity(intent);
            break;
        }
        return false;
    }
}
