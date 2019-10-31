package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;
import com.samlifttruck.activity.Utility.Consts;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;
import com.samlifttruck.activity.Models.CountingRegModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

import lib.kingja.switchbutton.SwitchMultiButton;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CountingRegActivity extends AppCompatActivity {
    private SwitchMultiButton mSwitchMultiButton;
    private static final String TAG = "CountingRegActivity";
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private TextView etFanniNumb;
    private TextInputEditText etProductName, etShelfNum, etCounting1, etCounting2, etCounting3, etResult_1_2, etFinalResult, etInventory;
    private Button btnSave;
    private int myProductCode = 0;
    private ImageButton btnCountingRegList;
    List<JSONObject> list = null;
    ProgressBar progressBar;
    CountingRegModel model;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_reg);


        setupViews();
        setToolbarText();


        setActivityContentBundled();
        etCounting2.setEnabled(false);
        etCounting3.setEnabled(false);


        mSwitchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                if (position == 2) {
                    etCounting1.setEnabled(true);
                    etCounting1.requestFocus();
                    Utility.showKeyPad(CountingRegActivity.this);
                    etCounting2.setEnabled(false);
                    etCounting3.setEnabled(false);

                } else if (position == 1) {
                    etCounting2.setEnabled(true);
                    etCounting2.requestFocus();
                    Utility.showKeyPad(CountingRegActivity.this);
                    etCounting1.setEnabled(false);
                    etCounting3.setEnabled(false);

                } else if (position == 0) {
                    etCounting3.setEnabled(true);
                    etCounting3.requestFocus();
                    Utility.showKeyPad(CountingRegActivity.this);
                    etCounting1.setEnabled(false);
                    etCounting2.setEnabled(false);
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFilled()){
                    registerCounting();
                }
            }
        });

    }

    private boolean isFilled() {
        if (etCounting1.getText().toString().equals("")) {
            etCounting1.requestFocus();
            etCounting1.setError(getString(R.string.please_fill) + getString(R.string.put_zero));
            return false;
        } else if (etCounting2.getText().toString().equals("")) {
            etCounting2.requestFocus();
            etCounting2.setError(getString(R.string.please_fill) + getString(R.string.put_zero));
            return false;
        } else if (etCounting3.getText().toString().equals("")) {
            etCounting3.requestFocus();
            etCounting3.setError(getString(R.string.please_fill) + getString(R.string.put_zero));
            return false;
        } else {
            return true;
        }
    }

    private void setActivityContentBundled() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();

            etInventory.setText(String.valueOf(extras.getInt(Consts.CntReg.ON_HAND)));
            etProductName.setText(extras.getString(Consts.CntReg.PRODUCT_NAME));
            etFanniNumb.setText(extras.getString(Consts.CntReg.TECH_NO));
            etShelfNum.setText(extras.getString(Consts.CntReg.SHELF_NUM));
            etCounting1.setText(String.valueOf(extras.getInt(Consts.CntReg.COUNT_1)));
            etCounting2.setText(String.valueOf(extras.getInt(Consts.CntReg.COUNT_2)));
            etCounting3.setText(String.valueOf(extras.getInt(Consts.CntReg.COUNT_3)));
            etResult_1_2.setText(String.valueOf(extras.getInt(Consts.CntReg.RESULT_1_2)));
            etFinalResult.setText(String.valueOf(extras.getInt(Consts.CntReg.FINAL_RESULT)));
            myProductCode = extras.getInt(Consts.CntReg.PRODUCT_CODE);
        }

    }

    private void setupViews() {
        etFanniNumb = findViewById(R.id.activity_counting_reg_layout_tech_no);
        mSwitchMultiButton = findViewById(R.id.activity_counting_reg_switchbutton);
        etProductName = findViewById(R.id.activity_counting_reg_product_name);
        etShelfNum = findViewById(R.id.activity_counting_reg_shelf_num);
        etCounting1 = findViewById(R.id.activity_counting_reg_counting_1);
        etCounting2 = findViewById(R.id.activity_counting_reg_counting_2);
        etCounting3 = findViewById(R.id.activity_counting_reg_counting_3);
        etResult_1_2 = findViewById(R.id.activity_counting_reg_result_1_2);
        etFinalResult = findViewById(R.id.activity_counting_reg_final_result);
        btnSave = findViewById(R.id.activity_counting_reg_btn_save);
        etInventory = findViewById(R.id.activity_counting_reg_inventory);
    }


    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.sabt_shomaresh));
    }

    private void registerCounting() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("executetype");
        p1.setValue(2);
        p1.setType(Byte.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("productCode");
        p2.setValue(myProductCode);
        p2.setType(Integer.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("count1");
        p3.setValue(Integer.valueOf(etCounting1.getText().toString()));
        p3.setType(Integer.class);

        PropertyInfo p4 = new PropertyInfo();
        p4.setName("count2");
        p4.setValue(Integer.valueOf(etCounting2.getText().toString()));
        p4.setType(Integer.class);

        PropertyInfo p5 = new PropertyInfo();
        p5.setName("count3");
        p5.setValue(Integer.valueOf(etCounting3.getText().toString()));
        p5.setType(Integer.class);

        PropertyInfo p6 = new PropertyInfo();
        p6.setName("countresault");
        p6.setValue(Integer.valueOf(etResult_1_2.getText().toString()));
        p6.setType(Integer.class);

        PropertyInfo p7 = new PropertyInfo();
        p7.setName("resault");
        p7.setValue(Integer.valueOf(etFinalResult.getText().toString()));
        p7.setType(Integer.class);

        PropertyInfo p8 = new PropertyInfo();
        p8.setName("shelf");
        p8.setValue(etShelfNum.getText().toString());
        p8.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_UPDATE_COUNTING_REG);
        ss.execute(p0, p1, p2, p3, p4, p5, p6, p7, p8);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();
                    CountingRegActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {
                                try {
                                    if (list.get(0).getString("boolean").equals("true")) {
                                        Toast.makeText(CountingRegActivity.this, "مورد با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    finish();
                                                }
                                            }, 2000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(CountingRegActivity.this, "خطا در ثبت", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: "+ e.getLocalizedMessage());
                    Toast.makeText(CountingRegActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });
    }


    public void onBackBtnClick(View view) {
        finish();
    }
}
