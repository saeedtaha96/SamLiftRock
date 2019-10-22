package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CustomSpinnerAdapter;
import org.json.JSONObject;
import java.util.List;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class RegDFPActivity extends AppCompatActivity {
    PersianDatePickerDialog datepicker;
    Spinner spinner;
    TextInputEditText tietDate;
    List<JSONObject> list = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_dfp);
        findViews();

        initSpinner();

        tietDate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() <= (tietDate.getLeft() + (4*tietDate.getCompoundDrawables()[DRAWABLE_LEFT].getDirtyBounds().width()))) {
                        // your action here
                        Toast.makeText(RegDFPActivity.this, "hi", Toast.LENGTH_SHORT).show();
                        showDatePicker();
                        return true;
                    }
                }
                return false;
            }
        });


    }

    private void showDatePicker() {
        PersianCalendar initDate = new PersianCalendar();
        int day = initDate.getPersianDay();
        int month = initDate.getPersianMonth();
        int year = initDate.getPersianYear();
        initDate.setPersianDate(year, month, day);

        // persian Date picker
        datepicker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("تایید")
                .setNegativeButton("بیخیال")
                .setTodayButton("برو به امروز")
                .setTodayButtonVisible(true)
                .setInitDate(initDate)
                .setTitleColor(getColor(R.color.color_red_refuse))
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1380)
                .setActionTextColor(getColor(R.color.color_blue_theme_004677))
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        int month = persianCalendar.getPersianMonth();
                        int day = persianCalendar.getPersianDay();
                        String formattedMonth = (month < 10) ? ("0" + month) : String.valueOf(month);
                        String formattedDay = (day < 10) ? ("0" + day) : String.valueOf(day);

                        String myDate = persianCalendar.getPersianYear() + "/" + formattedMonth + "/" + formattedDay;
                        tietDate.setText(myDate);
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        datepicker.show();
    }

    private void initSpinner() {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, new String[]{"یک", "دو", "سه", "چهار"}, CustomSpinnerAdapter.SPINNER_DRAFT_TYPE);
        spinner.setAdapter(adapter);
    }

    private void findViews() {
        spinner = findViewById(R.id.activity_reg_dfp_spinner);
        tietDate = findViewById(R.id.activity_reg_dfp_date);
    }


    public void onBackBtnClick(View view) {
        onBackPressed();
    }



}
