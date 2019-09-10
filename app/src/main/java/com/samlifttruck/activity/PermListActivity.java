package com.samlifttruck.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.PermListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;
import com.samlifttruck.activity.Fragments.PermListFragment;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class PermListActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etDate;
    ImageView datepickerImgv;
    PersianDatePickerDialog datepicker;
    PermListFragment permListFragment;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perm_list);
        setToolbarText();
        setupViews();

        permListFragment = PermListFragment.newInstance((etDate != null) ? etDate.getText().toString().trim() : "1398/08/02");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_perm_list, permListFragment).commit();
        datepickerImgv.setOnClickListener(this);


        etDate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etDate.getRight() - 25 - etDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (etDate.getText().toString().equals("")) {
                            etDate.setError("خالی است");
                        } else {
                            // new ShelfEditActivity.soapCall().execute("x4fg54-D9ib", etDate.getText().toString());
                        }

                        closeKeyPad();

                        return true;
                    }
                }
                return false;
            }
        });
        //etDate.setText(getToday());
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

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_perm_list));
    }

    private String getToday() {
        PersianCalendar initDate = new PersianCalendar();
        int day = initDate.getPersianDay();
        int month = initDate.getPersianMonth();
        int year = initDate.getPersianYear();
        String formattedMonth = (month < 10) ? ("0" + month) : String.valueOf(month);
        String formattedDay = (day < 10) ? ("0" + day) : String.valueOf(day);

        String myDate = year + "/" + formattedMonth + "/" + formattedDay;
        return myDate;
    }

    private void setupViews() {
        etDate = findViewById(R.id.activity_datepicker_input_datepicker);
        datepickerImgv = findViewById(R.id.activity_datepicker_imgv_datepicker);
    }


    @Override
    public void onClick(View view) {
        int v = view.getId();

        if (view.equals(datepickerImgv)) {
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
        onBackPressed();
    }
}

