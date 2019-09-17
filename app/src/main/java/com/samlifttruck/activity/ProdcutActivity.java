package com.samlifttruck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CustomSpinnerAdapter;

import org.angmarch.views.NiceSpinner;
import org.json.JSONObject;

import java.util.List;


public class ProdcutActivity extends AppCompatActivity {
    Spinner typeSpinner, unitSpinner;
    CustomSpinnerAdapter adapter;
    List<JSONObject> list = null;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodcut);

        setupViews();
        setToolbarText();


        String[] ITEMS = {"سلام", "بیست", "چهل", "نان", "فنی", "ادبیات"};
        adapter = new CustomSpinnerAdapter(this, ITEMS);

        setupSpinnerOfType();
        setupSpinnerofUnit();


    }

    private void setToolbarText() {
        AppBarLayout appBar = findViewById(R.id.activity_shelf_toolbar);
        TextView tvAppbar = appBar.findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.menu_txt_product));
    }

    private void setupSpinnerofUnit() {
        unitSpinner.setAdapter(adapter);
        unitSpinner.setBackground(getDrawable(R.drawable.spinner_cars));
    }

    private void setupSpinnerOfType() {
        typeSpinner.setAdapter(adapter);
        typeSpinner.setBackground(getDrawable(R.drawable.spinner_cars));
    }

    private void setupViews() {
        typeSpinner = findViewById(R.id.spinner_type);
        unitSpinner = findViewById(R.id.spinner_unit);


    }

    public void onBackBtnClick(View view) {
        finish();
    }



}
