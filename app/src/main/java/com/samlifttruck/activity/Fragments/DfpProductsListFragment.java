package com.samlifttruck.activity.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DfpProductDetailsAdapter;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;
import com.samlifttruck.activity.Models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class DfpProductsListFragment extends Fragment {

    private static final String KEY_BUSINESS_ID = "businessId";

    private int businessId, userId;

    List<DetailsModel> detailsList;
    RecyclerView rv;


    public DfpProductsListFragment() {
        // Required empty public constructor
    }

    public static DfpProductsListFragment newInstance(int mBusinessId) {
        DfpProductsListFragment fragment = new DfpProductsListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_BUSINESS_ID, mBusinessId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            businessId = getArguments().getInt(KEY_BUSINESS_ID);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPermProductList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dfp_products_list, container, false);
        rv = view.findViewById(R.id.fragment_dfp_products_list_rv);


        return view;
    }

    private void getPermProductList() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("userId");
        p1.setValue(userId);
        p1.setType(Integer.class);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("businessId");
        p2.setValue(businessId);
        p2.setType(Integer.class);

        final SoapCall ss = new SoapCall((AppCompatActivity) getActivity(), SoapCall.METHOD_GET_DRAFT_FROM_PERM);
        ss.execute(p0, p1, p2);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    final List<JSONObject> list = ss.get();
                    if (getActivity() != null) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (list != null) {

                                    detailsList = new ArrayList<>(list.size());
                                    DetailsModel model;
                                    for (int i = 0; i < list.size(); i++) {
                                        model = new DetailsModel();
                                        try {
                                            model.setUnitName(list.get(i).getString("unitName"));
                                            model.setTechNo(list.get(i).getString("techNo"));
                                            model.setProductName(list.get(i).getString("productName"));
                                            model.setOnHand(list.get(i).getInt("onHand"));
                                            model.setProductCode(list.get(i).getInt("productCode"));
                                            model.setQty(list.get(i).getInt("qty"));
                                            detailsList.add(model);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // TODO: set the RecyclerView for product details

                                    DfpProductDetailsAdapter adapter = new DfpProductDetailsAdapter(getActivity(), detailsList);
                                    rv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                    rv.setAdapter(adapter);

                                    //

                                } else {
                                    Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_SHORT).show();
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
