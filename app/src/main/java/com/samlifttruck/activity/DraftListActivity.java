package com.samlifttruck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DraftListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class DraftListActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etDate;
    ImageView datepickerImgv;
    PersianDatePickerDialog datepicker;
    RecyclerView rvDraftList;
    DraftListAdapter draftListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_list);

        setupViews();

        draftListAdapter = new DraftListAdapter(DataGenerator.getDraftList());
        rvDraftList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
        rvDraftList.setAdapter(draftListAdapter);
        datepickerImgv.setOnClickListener(this);


        //  setToday();
    }

    /*private void setToday() {
        int month = persianCalendar.getPersianMonth();
        int day = persianCalendar.getPersianDay();
        String formattedMonth = (month < 10) ? ("0" + month) : String.valueOf(month);
        String formattedDay = (day < 10) ? ("0" + day) : String.valueOf(day);

        String myDate = persianCalendar.getPersianYear() + "/" + formattedMonth + "/" + formattedDay;
        etDate.setText(myDate);
    }*/

    private void setupViews() {
        etDate = findViewById(R.id.login_input_datepicker);
        datepickerImgv = findViewById(R.id.activity_draft_imgv_datepicker);
        rvDraftList = findViewById(R.id.activity_draft_recyclerview);
    }


    @Override
    public void onClick(View view) {
        int v = view.getId();

        if (v == R.id.activity_draft_imgv_datepicker) {
            PersianCalendar initDate = new PersianCalendar();
            initDate.setPersianDate(1398, 8, 17);
            datepicker = new PersianDatePickerDialog(DraftListActivity.this)
                    .setPositiveButtonString("باشه")
                    .setNegativeButton("بیخیال")
                    .setTodayButton("امروز")
                    .setTodayButtonVisible(true)
                    .setInitDate(initDate)
                    .setTitleColor(getColor(R.color.color_red_refuse))
                    .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                    .setMinYear(1300)
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
