package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CardexDetailsAdapter;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.CardexDetailsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CardexDeatilsActivity extends AppCompatActivity {
    RecyclerView rv;
    CardexDetailsAdapter cardexAdapter;
    List<JSONObject> list = null;
    ProgressBar progressBar;
    private static int INCOMING = 0;
    private static int OUTGOING = 0;
    private static int INVENTORY = 0;

    List<CardexDetailsModel> cardexList;
    private int myProductCode;
    private String myTechNo, myShelfNum, myProductName, myDate;
    TextView tvProductName, tvShelfNum, tvTechNum, tvIncoming, tvOutgoing, tvInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardex_deatils);
        progressBar = findViewById(R.id.activity_cardex_details_pbar);
        rv = findViewById(R.id.activity_cardex_details_rv);
        tvProductName = findViewById(R.id.activity_cardex_details_product_name);
        tvShelfNum = findViewById(R.id.activity_cardex_details_shelf_num);
        tvTechNum = findViewById(R.id.activity_cardex_details_tech_num);
        tvIncoming = findViewById(R.id.activity_cardex_details_incoming);
        tvOutgoing = findViewById(R.id.activity_cardex_details_outgoing);
        tvInventory = findViewById(R.id.activity_cardex_details_inventory);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            myDate = extras.getString("date");
            myProductName = extras.getString("productName");
            myProductCode = extras.getInt("productCode");
            myShelfNum = extras.getString("shelfNo");
            myTechNo = extras.getString("techNo");

            tvProductName.setText(myProductName);
            tvTechNum.setText(myTechNo);
            tvShelfNum.setText(myShelfNum);
            tvIncoming.setText("-");
            tvOutgoing.setText("-");
            tvInventory.setText("-");

            getCardexDetails(myProductCode, myDate);
        }


    }

    public void onBackBtnClick(View view) {
        onBackPressed();
    }

    private void getCardexDetails(int productCode, String date) {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("productCode");
        p1.setValue(productCode);
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("azpDate");
        p2.setValue(date + "/01/01");
        p2.setType(String.class);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("tapDate");
        p3.setValue(date + "/12/29");
        p3.setType(String.class);

        final SoapCall ss = new SoapCall(this, SoapCall.METHOD_GET_CARDEX);
        ss.execute(p0, p1, p2, p3);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ss.get() != null) {
                        list = ss.get();
                        cardexList = new ArrayList<>(list.size());
                        CardexDetailsModel model;
                        for (int i = 0; i < list.size(); i++) {

                            model = new CardexDetailsModel();

                            model.setID(list.get(i).getString("BusinessName") + " شماره  " + list.get(i).getString("BusinessNominal"));
                            model.setPersonName(list.get(i).getString("PersonName"));
                            model.setPersianBusinessDate(list.get(i).getString("PersianBusinessDate"));
                            model.setIncoming(list.get(i).getInt("Incoming"));
                            model.setOutGoing(list.get(i).getInt("OutGoing"));
                            model.setOnHand(list.get(i).getInt("OnHand"));
                            INCOMING += model.getIncoming();
                            OUTGOING += model.getOutGoing();

                            cardexList.add(model);
                        }
                        INVENTORY = INCOMING - OUTGOING;
                        tvIncoming.setText(String.valueOf(INCOMING));
                        tvOutgoing.setText(String.valueOf(OUTGOING));
                        tvInventory.setText(String.valueOf(INVENTORY));
                        rv.setLayoutManager(new LinearLayoutManager(CardexDeatilsActivity.this, RecyclerView.VERTICAL, false));
                        cardexAdapter = new CardexDetailsAdapter(cardexList);
                        rv.setAdapter(cardexAdapter);

                    } else if (ss.get() == null) {
                        Toast.makeText(CardexDeatilsActivity.this, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                    }

                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CardexDeatilsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
