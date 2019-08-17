package com.samlifttruck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupView();
        setupListeners();
    }


    private void setupView() {
        etUsername = findViewById(R.id.login_input_username);
        etPassword = findViewById(R.id.login_input_password);
        btnLogin = findViewById(R.id.login_btn_login);
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
}
