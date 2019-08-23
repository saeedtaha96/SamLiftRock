package com.samlifttruck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.PermListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class PermListActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etDate;
    ImageView datepickerImgv;
    PersianDatePickerDialog datepicker;
    RecyclerView rvPermList;
    PermListAdapter PermListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perm_list);
        setToolbarText();
        setupViews();

        PermListAdapter = new PermListAdapter(DataGenerator.getPermList());
        rvPermList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
        rvPermList.setAdapter(PermListAdapter);
        datepickerImgv.setOnClickListener(this);


        setToday();
    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_perm_list));
    }

    private void setToday() {
        PersianCalendar initDate = new PersianCalendar();
        int day = initDate.getPersianDay();
        int month = initDate.getPersianMonth();
        int year = initDate.getPersianYear();
        String formattedMonth = (month < 10) ? ("0" + month) : String.valueOf(month);
        String formattedDay = (day < 10) ? ("0" + day) : String.valueOf(day);

        String myDate = year + "/" + formattedMonth + "/" + formattedDay;
        etDate.setText(myDate);
    }

    private void setupViews() {
        etDate = findViewById(R.id.login_input_datepicker);
        datepickerImgv = findViewById(R.id.activity_perm_imgv_datepicker);
        rvPermList = findViewById(R.id.activity_perm_recyclerview);
    }


    @Override
    public void onClick(View view) {
        int v = view.getId();

        if (v == R.id.activity_perm_imgv_datepicker) {
            //inital datepicker date
            PersianCalendar initDate = new PersianCalendar();
            int day = initDate.getPersianDay();
            int month = initDate.getPersianMonth();
            int year = initDate.getPersianYear();
            initDate.setPersianDate(year, month, day);

            // persian Date picker
            datepicker = new PersianDatePickerDialog(PermListActivity.this)
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
                            etDate.setText(myDate);
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });

            datepicker.show();
        }
    }

    public void onBackBtnClick(View view) {
        finish();
    }
}
