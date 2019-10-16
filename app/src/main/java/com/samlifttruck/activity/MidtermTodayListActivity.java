package com.samlifttruck.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CountingRegListAdapter;
import com.samlifttruck.activity.Adapters.MidtermTodayListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.DraftListModel;
import com.samlifttruck.activity.Models.MidtermControlModel;
import com.samlifttruck.activity.Models.ProductModel;
import com.samlifttruck.activity.Models.MidtermControlModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.oveissi.materialsearchview.MaterialSearchView;

public class MidtermTodayListActivity extends AppCompatActivity {

    RecyclerView rvMidtermList;
    MidtermTodayListAdapter midtermListAdapter;
    ImageButton btnSearch;
    MaterialSearchView msv;
    Button btnConfirm;
    private List<MidtermControlModel> midtermList;
    List<JSONObject> list = null;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midterm_today_list);

        rvMidtermList = findViewById(R.id.activity_midterm_today_list_recyclerview);
        btnConfirm = findViewById(R.id.activity_midterm_today_list_btn_confirm);
        btnSearch = findViewById(R.id.activity_midterm_imgv_search);
        msv = findViewById(R.id.searchview_material);


        setToolbarText();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msv.showSearch();
            }
        });

        msv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
             /*   if (list != null) {
                    List<ReceiptListModel> myList = new ArrayList<>();
                    for (ReceiptListModel item : list) {
                        if (item.getCondition().startsWith(query)) {
                            myList.add(item);
                        }
                    }

                    permListAdapter = new MidtermTodayListAdapter(MidtermTodayListActivity.this,myList);
                    rvPermList.setAdapter(permListAdapter);
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (rvMidtermList != null) {
                    List<MidtermControlModel> myList = new ArrayList<>();
                    for (MidtermControlModel item : midtermList) {
                        if (item.getTechNo().toLowerCase().startsWith(newText.toLowerCase())) {
                            myList.add(item);
                        }
                    }

                    midtermListAdapter = new MidtermTodayListAdapter(MidtermTodayListActivity.this, myList);

                    rvMidtermList.setAdapter(midtermListAdapter);
                }
                return false;
            }
        });

        //set up recyclerview


        // on scroll change and confirm btn hide listener
        rvMidtermList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scrolling up
                    btnConfirm.animate().translationY(300f).setDuration(300);
                } else {
                    btnConfirm.animate().translationY(0).setDuration(200);
                }
            }
        });

        getMidtermCountList();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvMidtermList != null) {
                    executeCycleCount();
                } else {
                    Toast.makeText(MidtermTodayListActivity.this, "موردی برای ثبت وجود ندارد", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.menu_txt_control));
    }

    public void onBackBtnClick(View view) {
        finish();
    }

    public void getMidtermCountList() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_CYCLE_COUNT_MIDDLE);
        ss.execute(p0);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    list = ss.get();

                    MidtermTodayListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                midtermList = new ArrayList<>(list.size());
                                MidtermControlModel model;

                                for (int i = 0; i < list.size(); i++) {
                                    model = new MidtermControlModel();
                                    try {
                                        model.setProductName(list.get(i).getString("ProductName"));
                                        model.setTechNo(list.get(i).getString("TechNo"));
                                        model.setCurrCount(list.get(i).getString("CurrentCount"));
                                        model.setInventory(list.get(i).getString("onHand"));
                                        model.setProductCode(list.get(i).getInt("ProductCode"));
                                        midtermList.add(model);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    midtermListAdapter = new MidtermTodayListAdapter(MidtermTodayListActivity.this, midtermList);
                                    Configuration config = getResources().getConfiguration();
                                    if (config.smallestScreenWidthDp >= 600) {
                                        rvMidtermList.setLayoutManager(new GridLayoutManager(MidtermTodayListActivity.this, 2));
                                    } else {
                                        rvMidtermList.setLayoutManager(new LinearLayoutManager(MidtermTodayListActivity.this, RecyclerView.VERTICAL, false));
                                    }
                                    rvMidtermList.setAdapter(midtermListAdapter);
                                }
                            } else {
                                rvMidtermList = null;
                                Toast.makeText(MidtermTodayListActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(MidtermTodayListActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void executeCycleCount() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_EXECUTE_MIDTERM_COUNT);
        ss.execute(p0);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    list = ss.get();

                    MidtermTodayListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null) {

                                try {
                                    if (list.get(0).getString("boolean").equals("true")) {
                                        Toast.makeText(getApplicationContext(), "موارد با موفقیت ثبت شدند", Toast.LENGTH_LONG).show();
                                        midtermListAdapter = new MidtermTodayListAdapter(MidtermTodayListActivity.this, null);
                                        Configuration config = getResources().getConfiguration();
                                        if (config.smallestScreenWidthDp >= 600) {
                                            rvMidtermList.setLayoutManager(new GridLayoutManager(MidtermTodayListActivity.this, 2));
                                        } else {
                                            rvMidtermList.setLayoutManager(new LinearLayoutManager(MidtermTodayListActivity.this, RecyclerView.VERTICAL, false));
                                        }
                                        rvMidtermList.setAdapter(midtermListAdapter);
                                        rvMidtermList = null;
                                    } else if (list.get(0).getString("boolean").equals("false")) {
                                        Toast.makeText(getApplicationContext(), "خطا در ثبت", Toast.LENGTH_LONG).show();
                                    } else if (list == null) {
                                        Toast.makeText(getApplicationContext(), "خطا در ثبت", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(MidtermTodayListActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}