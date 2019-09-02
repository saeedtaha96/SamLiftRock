package com.samlifttruck.activity.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.ReceiptListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

public class ReceiptListFragment extends Fragment {

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
        receiptListAdapter = new ReceiptListAdapter(DataGenerator.getReceiptList());
        rvReceiptList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvReceiptList.setAdapter(receiptListAdapter);
        return view;
    }



}
