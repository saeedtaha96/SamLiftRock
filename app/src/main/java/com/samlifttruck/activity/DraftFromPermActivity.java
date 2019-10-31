package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DraftFromPermAdapter;
import com.samlifttruck.activity.Models.PermListModel;
import com.samlifttruck.activity.Utility.DataGenerator;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class DraftFromPermActivity extends AppCompatActivity {
    private PersianDatePickerDialog datepicker;
    private EditText etDate;
    private ImageView btnDatePick;
    private List<JSONObject> list = null;
    private RecyclerView rv;
    private DraftFromPermAdapter adapter;
    private List<PermListModel> permList;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_from_perm);

        findViews();
        etDate.setText(getToday());
        getDfpList(getToday());
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
                            etDate.setError("لطفا تاریخ را وارد نمایید");
                            etDate.requestFocus();
                        } else if (etDate.length() != 10) {
                            etDate.setError(getString(R.string.enter_date_correctly));
                        } else {
                            getDfpList(etDate.getText().toString());

                        }

                        closeKeyPad();
                    }
                }
                return false;
            }
        });

        btnDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });


    }


    private void findViews() {
        btnDatePick = findViewById(R.id.activity_datepicker_imgv_datepicker);
        etDate = findViewById(R.id.activity_datepicker_input_datepicker);
        rv = findViewById(R.id.activity_dfp_rv);
    }

    private void closeKeyPad() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        etDate.setText(myDate);
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        datepicker.show();
    }

    public void onBackBtnClick(View view) {
        finish();
    }


    private void getDfpList(String date) {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("pDate");
        p1.setValue(date);
        p1.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_PERM_LIST_TO_CONVERT);
        ss.execute(p0, p1);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();
                    DraftFromPermActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                permList = new ArrayList<>(list.size());
                                PermListModel model;
                                for (int i = 0; i < list.size(); i++) {
                                    model = new PermListModel();
                                    try {
                                        model.setBusinessID(list.get(i).getInt("BusinessID"));
                                        model.setPermNum(list.get(i).getString("BusinessNominal"));
                                        model.setCustName(list.get(i).getString("PersonName"));
                                        model.setCondition(list.get(i).getString("StatusName"));
                                        model.setDate(list.get(i).getString("PersianBusinessDate"));
                                        model.setDescrip(list.get(i).getString("Description1"));
                                        permList.add(model);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                adapter = new DraftFromPermAdapter(DraftFromPermActivity.this, permList);
                                rv.setLayoutManager(new LinearLayoutManager(DraftFromPermActivity.this, RecyclerView.VERTICAL, false));
                                rv.setAdapter(adapter);

                            } else {
                                Toast.makeText(DraftFromPermActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(DraftFromPermActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
