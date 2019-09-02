package com.samlifttruck.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.MidtermTodayListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

import ir.oveissi.materialsearchview.MaterialSearchView;

public class MidtermTodayListActivity extends AppCompatActivity {

    RecyclerView rvPermList;
    MidtermTodayListAdapter PermListAdapter;
    ImageButton btnSearch;
    MaterialSearchView msv;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midterm_today_list);
        rvPermList = findViewById(R.id.activity_midterm_today_list_recyclerview);
        btnConfirm = findViewById(R.id.activity_midterm_today_list_btn_confirm);
        btnSearch = findViewById(R.id.activity_midterm_imgv_search);
        msv = findViewById(R.id.searchview_material);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msv.showSearch();
            }
        });

        msv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        setToolbarText();
        //set up recyclerview
        PermListAdapter = new MidtermTodayListAdapter(DataGenerator.getReceiptList());
        Configuration config = getResources().getConfiguration();

       if (config.smallestScreenWidthDp >= 600) {
            // sw600dp code goes here
           rvPermList.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else {
            // fall-back code goes here
           rvPermList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        }

        rvPermList.setAdapter(PermListAdapter);

        // on scroll change and confirm btn hide listener
        rvPermList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        tvAppbar.setText(getString(R.string.menu_txt_control));
    }

    public void onBackBtnClick(View view) {
        finish();
    }

}
