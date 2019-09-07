package com.samlifttruck.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CustomSpinnerAdapter;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class CardexActivity extends AppCompatActivity {
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
        initCustomSpinner();

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
                        } else {
                            if (chbxSetDate.isChecked()) {
                                s = spinnerDateChoose.getSelectedItem().toString();
                                Toast.makeText(CardexActivity.this, s, Toast.LENGTH_SHORT).show();
                            } else {
                                s = String.valueOf(year);
                                Toast.makeText(CardexActivity.this, s, Toast.LENGTH_SHORT).show();
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

    private void initCustomSpinner() {
        spinnerDateChoose.setBackground(getDrawable(R.drawable.spinner_cars_uncheck));
        spinnerDateChoose.setEnabled(false);
        calendar = new PersianCalendar();
        year = calendar.getPersianYear();
        String[] list = {String.valueOf(year - 1), String.valueOf(year - 2), String.valueOf(year - 3), String.valueOf(year - 4), String.valueOf(year - 5), String.valueOf(year - 6), String.valueOf(year - 7)};
        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(this, list);
        spinnerDateChoose.setAdapter(spinnerAdapter);
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
        chbxSetDate = findViewById(R.id.activity_cardex_checkbox);
        spinnerDateChoose = findViewById(R.id.spinner_cardex);
        etFanniNumb = findViewById(R.id.layout_et_tech_no);
    }


    public void onBackBtnClick(View view) {
        onBackPressed();
    }
}
