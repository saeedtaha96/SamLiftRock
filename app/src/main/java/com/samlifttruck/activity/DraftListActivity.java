package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;

public class DraftListActivity extends AppCompatActivity {
    TextInputEditText tiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_list);

        tiet = findViewById(R.id.login_input_datepicker);
    }
}
