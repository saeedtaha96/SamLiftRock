package com.samlifttruck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.samlifttruck.R;

import org.angmarch.views.NiceSpinner;


public class ProdcutActivity extends AppCompatActivity {
    NiceSpinner typeSpinner, unitSpinner;
    ArrayAdapter<String> adapter;
    CheckBox maliatCHBX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodcut);

        setupViews();
        setToolbarText();


        String[] ITEMS = {"سلام", "بیست", "چهل", "نان", "فنی", "ادبیات"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setupSpinnerOfType();
        setupSpinnerofUnit();

        maliatCHBX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    typeSpinner.setEnabled(true);
                    unitSpinner.setEnabled(true);
                    typeSpinner.setBackground(getDrawable(R.drawable.spinner_cars));
                    unitSpinner.setBackground(getDrawable(R.drawable.spinner_cars));
                } else {
                    typeSpinner.setEnabled(false);
                    unitSpinner.setEnabled(false);
                    typeSpinner.setBackground(getDrawable(R.drawable.spinner_cars_uncheck));
                    unitSpinner.setBackground(getDrawable(R.drawable.spinner_cars_uncheck));
                }
            }
        });

    }

    private void setToolbarText() {
        AppBarLayout appBar = findViewById(R.id.activity_shelf_toolbar);
        TextView tvAppbar = appBar.findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.menu_txt_product));
    }

    private void setupSpinnerofUnit() {
        unitSpinner.setAdapter(adapter);
        unitSpinner.setEnabled(false);
        typeSpinner.setBackground(getDrawable(R.drawable.spinner_cars));
    }

    private void setupSpinnerOfType() {
        typeSpinner.setAdapter(adapter);
        typeSpinner.setEnabled(false);
        typeSpinner.setBackground(getDrawable(R.drawable.spinner_cars));
    }

    private void setupViews() {
        typeSpinner = findViewById(R.id.spinner_type);
        unitSpinner = findViewById(R.id.spinner_unit);
        maliatCHBX = findViewById(R.id.product_checkbox_maliat);

    }

    public void onBackBtnClick(View view) {
        finish();
    }

    public void imgvExpandSpinner(View view) {
        unitSpinner.performClick();
    }


}
