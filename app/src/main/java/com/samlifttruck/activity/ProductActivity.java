package com.samlifttruck.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CustomSpinnerAdapter;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;
import com.samlifttruck.activity.Models.ProductTypeInfoModel;
import com.samlifttruck.activity.Models.UnitInfoModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ProductActivity extends AppCompatActivity {
    private Spinner typeSpinner, unitSpinner;
    private CustomSpinnerAdapter unitAdapter, productTypeAdapter;
    List<JSONObject> list = null;
    String[] unitItems;
    String[] productTypeItems;
    private int userID;
    private int unitID;
    int productTypeId;
    private List<UnitInfoModel> unitList;
    List<ProductTypeInfoModel> productTypeList;
    private SharedPreferences pref;
    Button btnReg;
    private EditText etProductName, etTechNum, etShelfNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodcut);

        pref = getSharedPreferences("myprefs", MODE_PRIVATE);
        if (pref.contains(Utility.LOGIN_USER_ID)) {
            userID = pref.getInt(Utility.LOGIN_USER_ID, 0);
        }
        setupViews();
        setToolbarText();
        getUnitInfo();
        //getProductTypeInfo();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFilled()) {
                    insertProduct();
                }

            }
        });


        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(ProductActivity.this, unitSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                getUnitID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getProductTypeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getUnitID() {
        for (UnitInfoModel m : unitList) {
            if (unitSpinner.getSelectedItem().toString().equals(m.getUnitName())) {
                unitID = m.getUnitID();

            }
        }
        //Toast.makeText(ProductActivity.this, String.valueOf(unitID), Toast.LENGTH_SHORT).show();
    }

    public void getProductTypeID() {
        for (ProductTypeInfoModel m : productTypeList) {
            if (typeSpinner.getSelectedItem().toString().equals(m.getProductTypeName())) {
                productTypeId = m.getProductTypeID();

            }
        }
        // Toast.makeText(ProductActivity.this, String.valueOf(productTypeId), Toast.LENGTH_SHORT).show();
    }

    private void setToolbarText() {
        AppBarLayout appBar = findViewById(R.id.activity_shelf_toolbar);
        TextView tvAppbar = appBar.findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.menu_txt_product));
    }

    private void setupSpinnerofUnit() {
        unitSpinner.setAdapter(unitAdapter);
        unitSpinner.setBackground(getDrawable(R.drawable.spinner_cars));
    }

    void setupSpinnerOfType() {
        typeSpinner.setAdapter(productTypeAdapter);
        typeSpinner.setBackground(getDrawable(R.drawable.spinner_cars));
    }

    private void setupViews() {
        btnReg = findViewById(R.id.activity_dfp_btn_reg);
        typeSpinner = findViewById(R.id.spinner_type);
        unitSpinner = findViewById(R.id.spinner_unit);
        etProductName = findViewById(R.id.product_name);
        etShelfNum = findViewById(R.id.product_shelf_number);
        etTechNum = findViewById(R.id.product_shomare_fanni);
    }

    public void onBackBtnClick(View view) {
        finish();
    }

    private void getProductTypeInfo() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_PRODUCT_TYPE_INFO);
        ss.execute(p0);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();
                    ProductActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                productTypeList = new ArrayList<>(list.size());
                                productTypeItems = new String[list.size()];
                                ProductTypeInfoModel model;
                                try {
                                    for (int i = 0; i < list.size(); i++) {
                                        model = new ProductTypeInfoModel();
                                        model.setProductTypeID(list.get(i).getInt("ProductTypeID"));
                                        model.setProductTypeName(list.get(i).getString("ProductTypeName"));
                                        productTypeList.add(model);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    productTypeItems[i] = productTypeList.get(i).getProductTypeName();
                                }
                                productTypeAdapter = new CustomSpinnerAdapter(getApplicationContext(), productTypeItems,CustomSpinnerAdapter.SPINNER_DATE);
                                setupSpinnerOfType();
                                productTypeId = productTypeList.get(0).getProductTypeID();

                            } else {
                                Toast.makeText(ProductActivity.this, "خطا در بارگیری DropDown", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(ProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUnitInfo() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_UNIT_INFO);
        ss.execute(p0);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();

                    ProductActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                unitList = new ArrayList<>(list.size());
                                UnitInfoModel model;
                                unitItems = new String[list.size()];
                                try {
                                    for (int i = 0; i < list.size(); i++) {
                                        model = new UnitInfoModel();
                                        model.setUnitID(list.get(i).getInt("UnitID"));
                                        model.setUnitName(list.get(i).getString("UnitName"));
                                        unitItems[i] = list.get(i).getString("UnitName");
                                        unitList.add(model);

                                    }
                                    unitAdapter = new CustomSpinnerAdapter(getApplicationContext(), unitItems,CustomSpinnerAdapter.SPINNER_DATE);
                                    setupSpinnerofUnit();
                                    unitID = list.get(0).getInt("UnitID");

                                    getProductTypeInfo();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } else {
                                Toast.makeText(ProductActivity.this, "خطا در بارگیری DropDown", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(ProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertProduct() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("isEditMode");
        p1.setValue(false);
        p1.setType(Boolean.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("txtTechNo");
        p2.setValue(etTechNum.getText().toString());
        p2.setType(String.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("txtProductName");
        p3.setValue(etProductName.getText().toString());
        p3.setType(String.class);

        PropertyInfo p4 = new PropertyInfo();
        p4.setName("unitId");
        p4.setValue(unitID);
        p4.setType(Integer.class);

        PropertyInfo p5 = new PropertyInfo();
        p5.setName("productType");
        p5.setValue(productTypeId);
        p5.setType(Integer.class);

        PropertyInfo p6 = new PropertyInfo();
        p6.setName("txtShelf");
        p6.setValue(etShelfNum.getText().toString());
        p6.setType(String.class);

        PropertyInfo p7 = new PropertyInfo();
        p7.setName("productCode");
        p7.setValue(0);
        p7.setType(Integer.class);

        PropertyInfo p8 = new PropertyInfo();
        p8.setName("userId");
        p8.setValue(userID);
        p8.setType(Integer.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_INSERT_PRODUCT);
        ss.execute(p0, p1, p2, p3, p4, p5, p6, p7, p8);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();
                    ProductActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {
                                try {
                                    if (list.get(0).getString("boolean").equals("true")) {
                                        Toast.makeText(ProductActivity.this, "مورد با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                                        etShelfNum.setText("");
                                        etProductName.setText("");
                                        etTechNum.setText("");
                                        etProductName.requestFocus();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(ProductActivity.this, "خطا در ثبت", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(ProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private boolean isFilled() {
        if (etProductName.getText().toString().equals("")) {
            etProductName.setError(getString(R.string.please_fill));
            etProductName.requestFocus();
            return false;
        } else if (etShelfNum.getText().toString().equals("")) {
            etShelfNum.setError(getString(R.string.please_fill));
            etShelfNum.requestFocus();
            return false;
        } else if (etTechNum.getText().toString().equals("")) {
            etTechNum.setError(getString(R.string.please_fill));
            etTechNum.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}