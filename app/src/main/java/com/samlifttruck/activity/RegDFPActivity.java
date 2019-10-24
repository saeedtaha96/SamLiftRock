package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CustomSpinnerAdapter;
import com.samlifttruck.activity.Adapters.PermListInfoAllAdapter;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class RegDFPActivity extends AppCompatActivity {
    private PersianDatePickerDialog datepicker;
    private Spinner spinner;
    private TextInputEditText tietDate;
    List<JSONObject> list = null;
    List<DetailsModel> detailsList;
    private PermListInfoAllAdapter permAdapter;
    Button btnObserveProducts,btnRegisterDFP;

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
                    if (event.getRawX() <= (tietDate.getLeft() + (4 * tietDate.getCompoundDrawables()[DRAWABLE_LEFT].getDirtyBounds().width()))) {
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
        btnObserveProducts = findViewById(R.id.activity_product_btn_observe_products);
        btnRegisterDFP = findViewById(R.id.activity_dfp_btn_reg);
    }


    public void onBackBtnClick(View view) {
        onBackPressed();
    }


    private void getPermProductList() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("userId");
        p1.setValue("");
        p1.setType(String.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("businessId");
        p2.setValue("");
        p2.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_DRAFT_FROM_PERM);
        ss.execute(p0, p1, p2);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    list = ss.get();

                    RegDFPActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                detailsList = new ArrayList<>(list.size());
                                DetailsModel model;
                                for (int i = 0; i < list.size(); i++) {
                                    model = new DetailsModel();
                                    try {
                                        model.setUnitName(list.get(i).getString("unitName"));
                                        model.setTechNo(list.get(i).getString("techNo"));
                                        model.setProductName(list.get(i).getString("productName"));
                                        model.setOnHand(list.get(i).getString("onHand"));
                                        model.setProductCode(list.get(i).getString("productCode"));
                                        model.setQty(list.get(i).getString("qty"));

                                        detailsList.add(model);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(RegDFPActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                // TODO: set the RecyclerView for product details


                                //

                            } else {
                                Toast.makeText(RegDFPActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(RegDFPActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void acceptBussiness() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("userId");
        p1.setValue("");
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("personId");
        p2.setValue("");
        p2.setType(Integer.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("txtCustomerName");
        p3.setValue("");
        p3.setType(String.class);

        PropertyInfo p4 = new PropertyInfo();
        p4.setName("pDate");
        p4.setValue("");
        p4.setType(String.class);

        PropertyInfo p5 = new PropertyInfo();
        p5.setName("businessTypes");
        p5.setValue("");
        p5.setType(Byte.class);

        PropertyInfo p6 = new PropertyInfo();
        p6.setName("mojavezId");
        p6.setValue("");
        p6.setType(Integer.class);

        PropertyInfo p7 = new PropertyInfo();
        p7.setName("txtDescription1");
        p7.setValue("");
        p7.setType(String.class);

        PropertyInfo p8 = new PropertyInfo();
        p8.setName("txtDescription2");
        p8.setValue("");
        p8.setType(String.class);

        PropertyInfo p9 = new PropertyInfo();
        p9.setName("txtDescription3");
        p9.setValue("");
        p9.setType(String.class);

        PropertyInfo p10 = new PropertyInfo();
        p10.setName("havaleType");
        p10.setValue("");
        p10.setType(Byte.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_ACCEPT_BUSINESS);
        ss.execute(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    list = ss.get();

                    RegDFPActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                try {
                                    if (list.get(0).getString("boolean").equals("true")) {
                                        Toast.makeText(RegDFPActivity.this, "مورد با موفقیت ثبت شد", Toast.LENGTH_LONG).show();

                                        // TODO: whats gonna do after this

                                        //
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(RegDFPActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(RegDFPActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
