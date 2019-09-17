package com.samlifttruck.activity.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.ReceiptListAdapter;
import com.samlifttruck.activity.DataGenerators.SoapCall;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.ReceiptListModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReceiptListFragment extends Fragment {
    List<JSONObject> list = null;
    List<ReceiptListModel> receiptList;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DATE = "date";

    // TODO: Rename and change types of parameters
    private String mDate;
    private RecyclerView rvReceiptList;
    private ReceiptListAdapter receiptListAdapter;

    public ReceiptListFragment() {
        // Required empty public constructor
    }


    public static ReceiptListFragment newInstance(String date) {
        ReceiptListFragment fragment = new ReceiptListFragment();
        Bundle args = new Bundle();
        args.putString(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDate = getArguments().getString(DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv_list, container, false);
        rvReceiptList = view.findViewById(R.id.activity_rv_list_recyclerview);
        getReceiptList();
        return view;
    }

    private void getReceiptList() {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("pDate");
        p1.setValue(mDate);
        p1.setType(String.class);

        final SoapCall ss = new SoapCall(null, SoapCall.METHOD_GET_RECEIPT_LIST, SoapCall.SOAP_ACTION_GET_RECEIPT_LIST);
        ss.execute(p0, p1);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ss.get() != null) {
                        list = ss.get();
                        receiptList = new ArrayList<>(list.size());
                        ReceiptListModel model;
                        for (int i = 0; i < list.size(); i++) {
                            model = new ReceiptListModel();
                            model.setReceiptNum(list.get(i).getString("BusinessNominal"));
                            model.setReceiptType(list.get(i).getString("HavalehTypeName"));
                            model.setProductSource(list.get(i).getString("PersonName"));
                            model.setCondition(list.get(i).getString("StatusName"));
                            model.setDate(list.get(i).getString("PersianBusinessDate"));
                            receiptList.add(model);
                        }

                        receiptListAdapter = new ReceiptListAdapter(receiptList);
                        rvReceiptList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
                        rvReceiptList.setAdapter(receiptListAdapter);

                    } else if (ss.get() == null) {
                        Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                    }

                } catch (ExecutionException | JSONException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
