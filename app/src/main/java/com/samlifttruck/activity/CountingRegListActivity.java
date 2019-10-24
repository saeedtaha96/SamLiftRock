package com.samlifttruck.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CountingRegListAdapter;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.CountingRegModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.oveissi.materialsearchview.MaterialSearchView;

public class CountingRegListActivity extends AppCompatActivity {
    private static final String TAG = "CountingRegListActivity";
    private RecyclerView rv;
    private CountingRegListAdapter adapter;
    private ImageButton btnSearch, btnScanQrCode;
    private MaterialSearchView msv;
    private List<CountingRegModel> countingRegModelList;
    List<JSONObject> list = null;
    ProgressBar progressBar;
    public static final int SCAN_RESULT_REQ = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_reg_list);

        findViews();

        setToolbarText();

        getProduct();


        btnScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CountingRegListActivity.this,QrCodeActivity.class);
                startActivityForResult(intent,SCAN_RESULT_REQ);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msv.showSearch();
            }
        });

        msv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
              /*  if (draftList != null) {
                    List<DraftListModel> myList = new ArrayList<>();
                    for (DraftListModel item : draftList) {
                        if (item.getCondition().toLowerCase().startsWith(query.toLowerCase())) {
                            myList.add(item);
                        }
                    }

                    adapter = new CountingRegListAdapter(myList);
                    rv.setAdapter(adapter);
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<CountingRegModel> myList = new ArrayList<>();
                if (countingRegModelList != null) {
                    for (CountingRegModel item : countingRegModelList) {
                        if (item.getTechNo().toLowerCase().startsWith(newText.toLowerCase())) {
                            myList.add(item);
                        }
                    }

                    adapter = new CountingRegListAdapter(myList);
                    rv.setAdapter(adapter);

                }
                return false;
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_RESULT_REQ) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    msv.showSearch();
                    msv.setQuery(data.getStringExtra("result"), false);
                }

            } else if (resultCode == RESULT_CANCELED) {
                // RESET THE VIEWS
            }
        }
    }

    private void findViews() {
        rv = findViewById(R.id.activity_counting_reg_list_recyclerview);
        btnSearch = findViewById(R.id.activity_midterm_imgv_search);
        msv = findViewById(R.id.searchview_material);
        btnScanQrCode = findViewById(R.id.activity_midterm_imgv_qrcode);
    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_counting_reg_list));
    }

    public void onBackBtnClick(View view) {
        finish();
    }


    private void getProduct() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_COUNTING_REG_LIST);
        ss.execute(p0);
        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    list = ss.get();
                    CountingRegListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {
                                countingRegModelList = new ArrayList<>(list.size());
                                CountingRegModel model;
                                for (int i = 0; i < list.size(); i++) {
                                    model = new CountingRegModel();
                                    try {
                                        model.setOnHand(list.get(i).getInt("onHand"));
                                        model.setProductCode(list.get(i).getInt("ProductCode"));
                                        model.setTechNo(list.get(i).getString("TechNo"));
                                        model.setShelfNum(list.get(i).getString("Shelf"));
                                        model.setProductName(list.get(i).getString("ProductName"));
                                        model.setCount1(list.get(i).getInt("Count1"));
                                        model.setCount2(list.get(i).getInt("Count2"));
                                        model.setCount3(list.get(i).getInt("Count3"));
                                        model.setCountResult_1_2(list.get(i).getInt("CountResault1"));
                                        model.setFinalResult(list.get(i).getInt("Resault"));
                                        countingRegModelList.add(model);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e(TAG, "error: " + e.getLocalizedMessage());
                                    }
                                }

                                adapter = new CountingRegListAdapter(countingRegModelList);
                                rv.setLayoutManager(new LinearLayoutManager(CountingRegListActivity.this, RecyclerView.VERTICAL, false));
                                //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
                                rv.setAdapter(adapter);

                            } else {
                                Toast.makeText(CountingRegListActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, "error: " + e.getLocalizedMessage());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(msv.isSearchOpen()){
            msv.closeSearch();
        }else {
           super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProduct();
    }
}
