package com.samlifttruck.activity.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.samlifttruck.R;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class DfpProductsEditFragment extends Fragment {

    private static final String KEY_PRODUCT_NAME = "productName";
    private static final String KEY_TECH_NO = "techNo";
    private static final String KEY_QTY = "qty";
    private static final String KEY_ON_HAND = "onHand";
    private static final String KEY_UNIT = "unit";
    private static final String KEY_PRODUCT_CODE = "productCode";
    private static final String KEY_UNIT_ID = "unitId";
    private static final String KEY_ROW = "row";

    TextInputEditText etTechNo, etProductName, etUnit, etOnHand, etQty;
    Button btnConfirm;
    private ImageButton register;

    List<JSONObject> list;

    private String mProductName;
    private String mTechNo;
    private int mProductCode;
    private int mQty;
    private int mOnHand;
    private String mUnitName;
    private int mUnitId;
    private int mUserId;
    private int mRow;


    public DfpProductsEditFragment() {
        // Required empty public constructor
    }

    public static DfpProductsEditFragment newInstance(String productName, String techNo, int productCode, int qty, int onHand, String unitName, int unitId, int row) {
        DfpProductsEditFragment fragment = new DfpProductsEditFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PRODUCT_NAME, productName);
        args.putString(KEY_TECH_NO, techNo);
        args.putInt(KEY_PRODUCT_CODE, productCode);
        args.putInt(KEY_ON_HAND, onHand);
        args.putInt(KEY_QTY, qty);
        args.putString(KEY_UNIT, unitName);
        args.putInt(KEY_UNIT_ID, unitId);
        args.putInt(KEY_ROW, row);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTechNo = getArguments().getString(KEY_TECH_NO);
            mProductName = getArguments().getString(KEY_PRODUCT_NAME);
            mProductCode = getArguments().getInt(KEY_PRODUCT_CODE);
            mQty = getArguments().getInt(KEY_QTY);
            mUnitName = getArguments().getString(KEY_UNIT);
            mOnHand = getArguments().getInt(KEY_ON_HAND);
            mUnitId = getArguments().getInt(KEY_UNIT_ID);
            mRow = getArguments().getInt(KEY_ROW);

            mUserId = Utility.returnPref(Objects.requireNonNull(getActivity())).getInt(Utility.LOGIN_USER_ID, 1);

            Toast.makeText(getActivity(), String.valueOf(mUserId) + String.valueOf(mRow), Toast.LENGTH_SHORT).show();




        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dfp_products_edit, container, false);

        etTechNo = view.findViewById(R.id.fragment_dfp_products_edit_tiet_tech_no);
        etProductName = view.findViewById(R.id.fragment_dfp_products_edit_tiet_product_name);
        etOnHand = view.findViewById(R.id.fragment_dfp_products_edit_tiet_onhand);
        etQty = view.findViewById(R.id.fragment_dfp_products_edit_tiet_qty);
        etUnit = view.findViewById(R.id.fragment_dfp_products_edit_tiet_unit);
        btnConfirm = view.findViewById(R.id.fragment_dfp_products_edit_btn_confirm);
        register = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar_imgbtn_reg_dfp);

        register.setVisibility(View.GONE);
        etProductName.setText(mProductName);
        etTechNo.setText(mTechNo);
        etUnit.setText(String.valueOf(mUnitName));
        etOnHand.setText(String.valueOf(mOnHand));
        etQty.setText(String.valueOf(mQty));


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etQty.getText().toString().equals("") && !(etQty.getText().toString().matches("\\d{10}"))) {
                    etQty.requestFocus();
                    etQty.setError(getResources().getString(R.string.please_fill));
                } else {
                    mQty = Integer.valueOf(etQty.getText().toString());
                    updateBusinessDetail(mQty);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        register.setVisibility(View.GONE);
    }

    private void updateBusinessDetail(int qty) {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("executeStatus");
        p1.setValue(2);
        p1.setType(Byte.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("userId");
        p2.setValue(mUserId);
        p2.setType(Integer.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("unitId");
        p3.setValue(mUnitId);
        p3.setType(Integer.class);

        PropertyInfo p4 = new PropertyInfo();
        p4.setName("row");
        p4.setValue(mRow);
        p4.setType(Integer.class);

        PropertyInfo p5 = new PropertyInfo();
        p5.setName("qty");
        p5.setValue(qty);
        p5.setType(Integer.class);

        PropertyInfo p6 = new PropertyInfo();
        p6.setName("priceId");
        p6.setValue(0);
        p6.setType(Integer.class);

        PropertyInfo p7 = new PropertyInfo();
        p7.setName("salePrice");
        p7.setValue(0);
        p7.setType(Integer.class);

        PropertyInfo p8 = new PropertyInfo();
        p8.setName("buyPrice");
        p8.setValue(0);
        p8.setType(Integer.class);

        PropertyInfo p9 = new PropertyInfo();
        p9.setName("productName");
        p9.setValue(mProductName);
        p9.setType(String.class);

        PropertyInfo p10 = new PropertyInfo();
        p10.setName("businessTypeId");
        p10.setValue(4);
        p10.setType(Integer.class);

        PropertyInfo p11 = new PropertyInfo();
        p11.setName("productCode");
        p11.setValue(mProductCode);
        p11.setType(Integer.class);

        PropertyInfo p12 = new PropertyInfo();
        p12.setName("rate");
        p12.setValue(0);
        p12.setType(Integer.class);

        PropertyInfo p13 = new PropertyInfo();
        p13.setName("orderBusinessId");
        p13.setValue(0);
        p13.setType(Integer.class);

        PropertyInfo p14 = new PropertyInfo();
        p14.setName("isAndroid");
        p14.setValue(true);
        p14.setType(Boolean.class);

        final SoapCall ss = new SoapCall((AppCompatActivity) getActivity(), SoapCall.METHOD_EXECUTE_TEMP_BUSINESS_DETAILS);
        ss.execute(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();
                    if (getActivity() != null) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (list != null) {

                                    try {
                                        if (list.get(0).getString("boolean").equals("true")) {
                                            Toast.makeText(getActivity(), "مورد با موفقیت آپدیت شد", Toast.LENGTH_LONG).show();

                                            // TODO: whats gonna do after this
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                }
                                            }, 1500);
                                            //
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "خطا در حذف", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
