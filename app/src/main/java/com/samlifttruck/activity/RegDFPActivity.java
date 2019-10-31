package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DfpProductDetailsAdapter;
import com.samlifttruck.activity.Utility.Consts;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;
import com.samlifttruck.activity.Fragments.DraftListInfoAllFragment;
import com.samlifttruck.activity.Models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RegDFPActivity extends AppCompatActivity {
    private List<JSONObject> myList = null;

    private List<DetailsModel> detailsList;
    private ImageButton btnRegisterDFP;
    static private String custName, condition, date, permNum, preFactorNum, descrip;
    static private int mUserId;
    private int businessId;
    public static int STATIC_DRAFT_TYPE = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_dfp);
        findViews();
        setToolbarText();
        setListeners();

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();

            custName = bundle.getString(Consts.dfp.CUST_NAME);
            date = bundle.getString(Consts.dfp.DATE);
            permNum = bundle.getString(Consts.dfp.PERM_NUM);
            businessId = bundle.getInt(Consts.dfp.BUSINESS_ID);
            descrip = bundle.getString(Consts.dfp.DESCRIPTION);

            SharedPreferences pref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
            mUserId = pref.getInt(Utility.LOGIN_USER_ID, 1);

            Toast.makeText(this, String.valueOf(businessId) + "\n" + String.valueOf(mUserId), Toast.LENGTH_SHORT).show();


            makeTemplate();

            DraftListInfoAllFragment frag = DraftListInfoAllFragment.newInstance(DraftListInfoAllFragment.ACTIVITY_DFP, businessId, " ", permNum, custName, date, " ", " ", descrip);
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_reg_dfp_container, frag).commit();
        }


    }

    private void setListeners() {

    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_draft_from_perm));
    }


    private void findViews() {
        btnRegisterDFP = findViewById(R.id.toolbar_imgbtn_reg_dfp);
        btnRegisterDFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void onBackBtnClick(View view) {
        onBackPressed();
    }

    private void makeTemplate() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("userId");
        p1.setValue(mUserId);
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("businessId");
        p2.setValue(businessId);
        p2.setType(Integer.class);


        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_DRAFT_FROM_PERM);
        ss.execute(p0, p1, p2);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    final List<JSONObject> list = ss.get();

                    RegDFPActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {
                                try {
                                    if (list.get(0).getString("boolean").equals("true")) {
                                      //  Toast.makeText(RegDFPActivity.this, "تبدیل مجوز به حواله...", Toast.LENGTH_SHORT).show();

                                    } else if (list.get(0).getString("boolean").equals("false")) {
                                        Toast.makeText(RegDFPActivity.this, "خطا در ثبت", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(RegDFPActivity.this, "خطا در ثبت", Toast.LENGTH_LONG).show();
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
