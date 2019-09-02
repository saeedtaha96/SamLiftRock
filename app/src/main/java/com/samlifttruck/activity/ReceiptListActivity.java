package com.samlifttruck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.ReceiptListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;
import com.samlifttruck.activity.Fragments.ReceiptListFragment;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ReceiptListActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etDate;
    ImageView datepickerImgv;
    PersianDatePickerDialog datepicker;
    ReceiptListFragment receiptListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);
        setToolbarText();
        setupViews();

        receiptListFragment = ReceiptListFragment.newInstance((etDate != null) ? etDate.getText().toString().trim() : "1398/08/02");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_receipt_list, receiptListFragment).commit();

        datepickerImgv.setOnClickListener(this);


        setToday();
    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_receipt_list));
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
        datepickerImgv = findViewById(R.id.activity_receipt_imgv_datepicker);
    }


    @Override
    public void onClick(View view) {
        int v = view.getId();

        if (v == R.id.activity_receipt_imgv_datepicker) {
            //inital datepicker date
            PersianCalendar initDate = new PersianCalendar();
            int day = initDate.getPersianDay();
            int month = initDate.getPersianMonth();
            int year = initDate.getPersianYear();
            initDate.setPersianDate(year, month, day);

            // persian Date picker
            datepicker = new PersianDatePickerDialog(ReceiptListActivity.this)
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

