package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.appbar.AppBarLayout;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CardexDetailsAdapter;

import org.json.JSONObject;

import java.util.List;

public class CardexDeatilsActivity extends AppCompatActivity {
    RecyclerView rv;
    CardexDetailsAdapter cardexAdapter;
    List<JSONObject> list = null;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardex_deatils);

        rv = findViewById(R.id.activity_cardex_details_rv);

        rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        cardexAdapter = new CardexDetailsAdapter();
        rv.setAdapter(cardexAdapter);
    }

    public void onBackBtnClick(View view) {
        onBackPressed();
    }
}
