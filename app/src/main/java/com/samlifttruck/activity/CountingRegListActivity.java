package com.samlifttruck.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CountingRegListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;
import com.samlifttruck.activity.Models.DraftListModel;

import java.util.ArrayList;
import java.util.List;

import ir.oveissi.materialsearchview.MaterialSearchView;

public class CountingRegListActivity extends AppCompatActivity {

    private RecyclerView rv;
    private CountingRegListAdapter adapter;
    private ImageButton btnSearch;
    private MaterialSearchView msv;
    private Button btnConfirm;
    private List<DraftListModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_reg_list);

        setToolbarText();

        list = DataGenerator.getDraftList();
        rv = findViewById(R.id.activity_counting_reg_list_recyclerview);
        btnSearch = findViewById(R.id.activity_midterm_imgv_search);
        msv = findViewById(R.id.searchview_material);
        btnConfirm = findViewById(R.id.activity_counting_reg_list_btn_confirm);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msv.showSearch();
            }
        });

        msv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (list != null) {
                    List<DraftListModel> myList = new ArrayList<>();
                    for (DraftListModel item : list) {
                        if (item.getCondition().startsWith(query)) {
                            myList.add(item);
                        }
                    }

                    adapter = new CountingRegListAdapter(myList);
                    rv.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<DraftListModel> myList = new ArrayList<>();
                for (DraftListModel item : list) {
                    if (item.getCondition().startsWith(newText)) {
                        myList.add(item);
                    }
                }

                adapter = new CountingRegListAdapter(myList);
                rv.setAdapter(adapter);
                return false;
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        adapter = new CountingRegListAdapter(list);
        rv.setAdapter(adapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_counting_reg_list));
    }

    public void onBackBtnClick(View view) {
        finish();
    }
}
